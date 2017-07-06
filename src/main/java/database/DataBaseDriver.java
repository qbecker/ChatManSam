package database;

import java.sql.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import utils.Logger.Log;

public class DataBaseDriver {
	
	public static Connection ConnectAndCreateDb() {
		Connection conn = null;
		try {
			String dbURL = "jdbc:sqlite:serverDB.db";
			conn = DriverManager.getConnection(dbURL);
			Statement setup = conn.createStatement();
			setup.execute(Schema.SERVERSCHEMA);
			Log.debug("DB initialized");
		}catch(Exception e) {
			e.printStackTrace();
		} 
		
		return conn;
	}
}
