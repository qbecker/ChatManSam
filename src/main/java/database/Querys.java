package database;

public class Querys {
	public static String insertUser = "INSERT INTO USERS(username,password,status) VALUES(?,?,?)";
	public static String getUserPass = "SELECT password FROM USERS WHERE username = ? ";
	public static String getUserName = "SELECT username FROM USERS WHERE username = ? ";

}
