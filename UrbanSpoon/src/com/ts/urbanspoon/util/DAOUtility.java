package com.ts.urbanspoon.util;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.ts.urbanspoon.exception.UrbanspoonException;

public class DAOUtility {
	private static final String INVALID_ARGUMENT_EXCEPTION = "Invalid Argument to close() method of class DAOUtility !!!";
	
	public static Connection getConncetion() {
		
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/urbanspoon", "root", "2311");
			System.out.println("**connection:"+connection);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;

	}
	public  static void close(Object... objects ) throws UrbanspoonException{
		if(null != objects && objects.length != 0){		
			try{
				for (Object object : objects) {
					if(null != object){
						if(object instanceof Connection){
							((Connection)object).close();
						} else if(object instanceof Statement){
							((Statement)object).close();
						} else if(object instanceof PreparedStatement){
							((PreparedStatement)object).close();
						} else if(object instanceof CallableStatement){
							((CallableStatement)object).close();
						} else if(object instanceof ResultSet){
							((ResultSet)object).close();
						} else {						
							throw new UrbanspoonException(INVALID_ARGUMENT_EXCEPTION);
						}
					}
				}				
			}catch (SQLException e) {
				throw new UrbanspoonException(e.toString());
			}
		}
	}
	
	public static int getLatestId(String table) throws UrbanspoonException{
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = DAOUtility.getConncetion();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select max(user_id) from "+table);
			if(resultSet.next()){
				return resultSet.getInt(1);
			}			
		} catch (SQLException e) {
			throw new UrbanspoonException(e.toString());
		} finally {
			DAOUtility.close(resultSet,statement,connection);
		}
		return -1;
	}
	public static int getLatestRestaurantId(String string) throws UrbanspoonException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = DAOUtility.getConncetion();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select max(restaurant_id) from "+string);
			if(resultSet.next()){
				return resultSet.getInt(1);
			}			
		} catch (SQLException e) {
			throw new UrbanspoonException(e.toString());
		} finally {
			DAOUtility.close(resultSet,statement,connection);
		}
		return -1;
	}	


}