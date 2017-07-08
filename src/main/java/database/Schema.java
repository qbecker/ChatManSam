package database;

public class Schema {

	public static String SERVERSCHEMA = "CREATE TABLE IF NOT EXISTS USERS(" +
			"username text PRIMARY KEY NOT NULL,"  //PIMARY KEY NOT NULL = no dupes
			+"password text NOT NULL,"
			+"status text NOT NULL"
			+ ");";
}
