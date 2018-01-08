package com.ts.urbanspoon.service;

import java.io.File;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ts.urbanspoon.dao.BranchDAO;
import com.ts.urbanspoon.dao.RecipeDAO;
import com.ts.urbanspoon.dao.RestaurantDAO;
import com.ts.urbanspoon.dao.UserDAO;
import com.ts.urbanspoon.dto.Branch;
import com.ts.urbanspoon.dto.Recipe;
import com.ts.urbanspoon.dto.Restaurant;
import com.ts.urbanspoon.dto.User;
import com.ts.urbanspoon.exception.UrbanspoonException;

public class UrbanSpoonService {
	private static final String IMAGESLOCATION = "D:\\Urbanspoon\\Workspace\\Urbanspoon\\WebContent\\images";

	public UrbanSpoonService() {
		
	}

	public static List<Restaurant> getRestaurants(final int TOP) {
		
		List<Restaurant> restaurantsList = RestaurantDAO.getRestaurants(TOP);
		for (Restaurant restaurant : restaurantsList) {
			List<Branch> branchesList = new BranchDAO().getBranches(restaurant.getId());
			restaurant.setBranchesList(branchesList);
		}

		return restaurantsList;
	}
	
	public static  List<Recipe> getRecipes(final int TOP){
		
		return new RecipeDAO().getRecipes(TOP);
	}

   public static User insertUser(HttpServletRequest request, HttpServletResponse response) throws UrbanspoonException{
		
		String name = request.getParameter("firstName") +" "+ request.getParameter("lastName");
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		if(gender.equalsIgnoreCase("male")){
			gender="M";
		}else
			gender="F";
		
		String pswd = request.getParameter("password");
		Date date = Date.valueOf(request.getParameter("date"));
		long number = Long.parseLong(request.getParameter("mobileNumber"));
		
		User u = new User();
		u.setName(name);
		u.setEmail(email);
		u.setGender(gender);
		u.setPassword(pswd);
		u.setDate(date);
		u.setMobileNo(number);
		
		User u1= UserDAO.insert(u);
		return u1;

   }

   public static User getUser(HttpServletRequest request, HttpServletResponse response) throws UrbanspoonException {
	   
	   User u = UserDAO.getUser(Integer.parseInt(request.getParameter("user_id")));
	   if(u.getPassword().equals(request.getParameter("password"))){
	
		   return u;
	   }
	   else
		   return null;

   }
   
   public static boolean insertRestaurant(List<FileItem> fileItemsList, HttpServletRequest request, HttpServletResponse response) throws UrbanspoonException{
		
	   Restaurant restaurant = new Restaurant();
		for (FileItem fileItem : fileItemsList) {
			if (fileItem.isFormField()) {
				if (fileItem.getFieldName().equals("govt_registration_id")) {
					restaurant.setGovtRegistrationId(fileItem.getString());
				} else if (fileItem.getFieldName().equals("name")) {
					restaurant.setName(fileItem.getString());
				} else if (fileItem.getFieldName().equals("password")) {
					restaurant.setPassword(fileItem.getString());
				}

			}
		}
		RestaurantDAO restaurantDAO = new RestaurantDAO();
		restaurant = restaurantDAO.insert(restaurant);
		if (restaurant.getId() != 0) {
			for (FileItem fileItem : fileItemsList) {

				storeImage(fileItem, "restaurants", restaurant.getId() + ".jpg");
				restaurantDAO.updateLogoAddress(restaurant.getId(), restaurant.getId() + ".jpg");
			}
			
		}
		return true;
  }
   
 private static boolean storeImage(FileItem fileItem, String string, String string2) {
	 if (null != fileItem) {
			try {
				String filePath = IMAGESLOCATION + "\\" + imageType + "\\" + fileName;
				fileItem.write(new File(filePath));
				return true;
			} catch (Exception e) {
				throw new UrbanspoonException(e.toString());
			}
		}
		return false;
	}

	
public static Restaurant getRestaurant(HttpServletRequest request, HttpServletResponse response) throws UrbanspoonException {
	   
	   Restaurant r = RestaurantDAO.getRestaurant(Integer.parseInt(request.getParameter("user_id")));
	   if(r.getPassword().equals(request.getParameter("password")))
	   return r;
	   else
	   return null;

   }

public static String getFormFeildValue(List<FileItem> fileItemsList, String fieldName) {
	if (fileItemsList != null) {
		for (FileItem fileItem : fileItemsList) {
			if (fileItem.getFieldName().equals(fieldName)) {
				return fileItem.getString();
			}
		}
	}
	return null;

}

public static List<FileItem> getFileItems(HttpServletRequest request) {
	DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
	ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
	try {
		return servletFileUpload.parseRequest(request);
	} catch (FileUploadException e) {
	}
	return null;

}



}