package database;

import java.sql.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import utils.Logger.Log;

public class DataBaseDriver {
	
	public static Connection ConnectAndCreateDb(DBType type, DBRole role) {
		String dbURL = "jdbc:sqlite:";
		
		Connection conn = null;
		try {
			switch(type) {
				case Test:
					if(role == DBRole.Client) {
						dbURL += "./build/serverDB.db";
					}else {
						dbURL += "./build/clientDB.db";
					}
					break;
				case Prod:
					if(role == DBRole.Client) {
						dbURL += "clientDB.db";
					}else {
						dbURL += "serverDB.db";
					}
					break;
			}
			
			conn = DriverManager.getConnection(dbURL);
			Statement setup = conn.createStatement();
			setup.execute(Schema.SERVERSCHEMA);
			Log.debug("DB initialized");
		}catch(Exception e) {
			e.printStackTrace();
		} 
		
		return conn;
	}
	public enum DBType{
		Test, Prod
	}
	public enum DBRole{
		Server, Client
	}
}







