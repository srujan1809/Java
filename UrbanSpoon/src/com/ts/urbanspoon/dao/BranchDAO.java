package com.ts.urbanspoon.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.ts.urbanspoon.dto.Branch;
import com.ts.urbanspoon.util.DAOUtility;

public class BranchDAO {
	
	public List<Branch> getBranches(int restaurantId){
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Branch> list = null;

		try {
			con = DAOUtility.getConncetion();
			String query = "select * from branch where restaurant_id = " +restaurantId;
			System.out.println("------>query is:" + query);
			stmt=con.createStatement();
			rs = stmt.executeQuery(query);
			System.out.println("rs:" + rs);
			list = new ArrayList<Branch>();
			
			if (rs.next()) {
				System.out.println("yes we have a record");
				do {
					  Branch b = new Branch();
					  b.setId(rs.getInt(1));
					  b.setLocation(rs.getString(2));
					  b.setCity(rs.getString(3));
					  b.setState(rs.getString(4));
					  b.setCountry(rs.getString(5));
					  b.setPostalCode(rs.getInt(6));
					  b.setEmail(rs.getString(7));
					  b.setMobileNo(rs.getLong(8));
					  
					  list.add(b);					 
				
				} while (rs.next());
			} 
			else {
				System.out.println("--->no record is available");
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return list;

	}

	public static List<Branch> getBranches(int int1, boolean b, boolean c) {
		return null;
	}
	 
}