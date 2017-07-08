package database;

import database.Querys;
import utils.Logger.Log;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class DAO{

	public static Connection conn = database.DataBaseDriver.ConnectAndCreateDb();

	public static boolean insert(String query, List params ) {
		boolean ret = true;
		synchronized(conn) {
			try {
				PreparedStatement insert = conn.prepareStatement(query);
				int i = 1;
				for(Object item : params ) {
					Log.debug(""+ i);
					insert.setObject(i, item);
					i++;
				}
				insert.executeUpdate();
			} catch (SQLException e) {
				ret = false;
				Log.debug(e.getMessage());
			}
		}
		return ret;
	}


	public static ResultSet get(String query, List params) {
		ResultSet result = null;
		synchronized(conn) {
			try {
				PreparedStatement get = conn.prepareStatement(query);
				int i = 1;
				for(Object item : params ) {
					//Log.debug(""+ i);
					get.setObject(i, item);
					i++;
				}
				result = get.executeQuery();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}


	public static boolean insertUser(String userName, String passWord, String status) {
		String[] parms = {userName, passWord, status};
		return insert(Querys.insertUser, Arrays.asList(parms));
	}

	// Argument is inputName because I wanted to know that it was from the scanner in ChatManSam.
	// I want to talk to you about getting a template for these get requests.
	public static String getUserName(String inputName) {
		String ret = null;
		ResultSet results = get(Querys.getUserName, Arrays.asList(new String[] {inputName}));
		try {
			ret = results.getString("username");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}


	public static String getPassWord(String userName) {
		String ret = null;
		ResultSet results = get(Querys.getUserPass, Arrays.asList(new String[] {userName}));
		try {
			ret = results.getString("password");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
