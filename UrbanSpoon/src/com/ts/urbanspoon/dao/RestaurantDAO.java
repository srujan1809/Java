package com.ts.urbanspoon.dao;
import java.sql.*;
import java.util.*;

import com.ts.urbanspoon.dto.*;
import com.ts.urbanspoon.exception.UrbanspoonException;
import com.ts.urbanspoon.util.DAOUtility;

public class RestaurantDAO {

	public static List<Restaurant> getRestaurants(final int TOP) {
		
		Connection con = null;		
		Statement stmt = null;
		ResultSet rs = null;
		List<Restaurant> list = null;

		try {
			con = DAOUtility.getConncetion();
			String query = "select * from restaurant limit " + TOP;
			System.out.println("------>query is:" + query);
			stmt=con.createStatement();
			rs = stmt.executeQuery(query);
			System.out.println("rs:" + rs);
			list = new ArrayList<Restaurant>();
			
			if (rs.next()) {
				System.out.println("yes we have a record");
				do {					
					  Restaurant r=new Restaurant();
					  r.setId(rs.getInt("restaurant_id"));
					  r.setGovtRegistrationId(rs.getString("govt_registration_id"));
					  r.setName(rs.getString("name"));
					  r.setLogoName(rs.getString("logo_name"));
					  
					  list.add(r);
				} while (rs.next());
			} else {
				System.out.println("--->no record is available");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public static Restaurant insert(Restaurant restaurant) throws UrbanspoonException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = DAOUtility.getConncetion();
			preparedStatement = connection
					.prepareStatement("insert into restaurant(govt_registration_id,name,password,logo_name) values(?,?,?,?)");
			preparedStatement.setString(1, restaurant.getGovtRegistrationId());
			preparedStatement.setString(2, restaurant.getName());
			preparedStatement.setString(3, restaurant.getPassword());
			preparedStatement.setString(4, restaurant.getLogoName());
			if (preparedStatement.executeUpdate() > 0) {
				restaurant.setId(DAOUtility.getLatestRestaurantId("restaurant"));
			}
		} catch (SQLException e) {
			throw new UrbanspoonException(e.toString());
		} finally {
			DAOUtility.close(resultSet, preparedStatement, connection);

		}
		return restaurant;
	}
	
	public static Restaurant getRestaurant(String govtRegistrationId, boolean includeBranches) throws UrbanspoonException {
		Restaurant restaurant = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DAOUtility.getConncetion();
			preparedStatement = connection.prepareStatement("select * from restaurant where govt_registration_id = ?");
			preparedStatement.setString(1, govtRegistrationId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				restaurant = new Restaurant();
				restaurant.setId(resultSet.getInt(1));
				restaurant.setGovtRegistrationId(resultSet.getString(2));
				restaurant.setName(resultSet.getString(3));
				restaurant.setPassword(resultSet.getString(4));
				restaurant.setLogoName(resultSet.getString(5));
				if (includeBranches) {
					restaurant.setBranchesList(BranchDAO.getBranches(resultSet.getInt(1), true,false));
				}
			}
		} catch (SQLException e) {
			throw new UrbanspoonException(e.toString());
		} finally {
			DAOUtility.close(resultSet, preparedStatement, connection);
		}
		return restaurant;
	}
	
	public static Restaurant getRestaurant(int restaurantId) throws UrbanspoonException {
		Restaurant restaurant = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DAOUtility.getConncetion();
			preparedStatement = connection.prepareStatement("select * from restaurant where restaurant_id = ?");
			preparedStatement.setInt(1, restaurantId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				restaurant = new Restaurant();
				restaurant.setId(resultSet.getInt(1));
				restaurant.setGovtRegistrationId(resultSet.getString(2));
				restaurant.setName(resultSet.getString(3));
				restaurant.setPassword(resultSet.getString(4));
				restaurant.setLogoName(resultSet.getString(5));
				
			}
		} catch (SQLException e) {
			throw new UrbanspoonException(e.toString());
		} finally {
			DAOUtility.close(resultSet, preparedStatement, connection);
		}
		return restaurant;
	}
	
	public boolean updateLogoAddress(int restaurantId, String fileName) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DAOUtility.getConncetion();
			preparedStatement = connection.prepareStatement("update restaurant set logo_name =? where restaurant_id = ?");
			preparedStatement.setString(1, fileName);
			preparedStatement.setLong(2, restaurantId);

			if (preparedStatement.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			throw new UrbanspoonException(e.toString());
		} finally {
			DAOUtility.close(preparedStatement, connection);

		}
		return false;
	}

		
	}
