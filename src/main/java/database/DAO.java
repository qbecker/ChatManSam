package database;

import database.Querys;
import utils.Logger.Log;

import java.sql.*;

public class DAO{
	
	public static Connection conn = database.DataBaseDriver.ConnectAndCreateDb();
	
	public static boolean insertUser(String userName, String passWord, String status) {
		boolean ret = true;
		synchronized(conn){
			try {
				PreparedStatement  insert = conn.prepareStatement(Querys.insertUser);
				insert.setString(1, userName);
				insert.setString(2, passWord);
				insert.setString(3, status);
				insert.executeUpdate();
				Log.debug("Inserted User " + userName );
			}catch(Exception e) {
				ret = false;
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	public static String getPassWord(String userName) {
		String ret = null;
		synchronized(conn) {
			try {
				PreparedStatement getPass = conn.prepareStatement(Querys.getUserPass);
				getPass.setString(1, userName);
				
				ResultSet result = getPass.executeQuery();
				while(result.next()) {
					ret = result.getString("password");
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
}