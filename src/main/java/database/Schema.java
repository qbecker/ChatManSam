package database;

public class Schema {
	
	public static String SERVERSCHEMA = "CREATE TABLE IF NOT EXISTS USERS(" +
			"username text,"
			+"password text NOT NULL,"
			+"status text NOT NULL"
			+ ");";
}
