package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	
	private static String url = "jdbc:mysql://localhost/wapdb";
	private static String user = "wapdbadmin";
	private static String password = "passerAdmin";
	private static Connection connection = null;
	
	public static Connection getConnection () {
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return connection;
	}
}
