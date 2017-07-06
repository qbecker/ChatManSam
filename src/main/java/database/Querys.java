package database;

public class Querys {
	public static String insertUser = "INSERT INTO USERS(username,password,status) VALUES(?,?,?)";
	public static String getUserPass = "SELECT password FROM USERS WHERE username = ? ";

}
