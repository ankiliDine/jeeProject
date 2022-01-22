package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.User;

public class UserDao {

	
	private static final String CHECK_EMAIL_REQUEST_PREP = "SELECT *FROM user WHERE EMAIL = ?";
	private static final String ADD_REQUEST_PREP = "INSERT INTO user (NAME, FIRST_NAME, EMAIL, PROFIL, PASSWORD, FUNCTION) values ( ? , ? , ? , ? , ? , ? )";
	
	public static boolean addUser(User user) {
		
		Connection connection = ConnectionManager.getConnection();
		
		try {
			
			PreparedStatement statement = DaoUtility.createStatement(connection, ADD_REQUEST_PREP, user.getName(), user.getFirstName(), user.getEmail(), user.getProfil(), user.getPassword(), user.getFunction());
			int resultat = statement.executeUpdate();
			
			DaoUtility.closeConnection(connection, statement);
			
			if(resultat != 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}
/*	
	public static boolean checkEmail(String email){
		
		Connection connection = ConnectionManager.getConnection();
		try {
			
			PreparedStatement statement = DaoUtility.createStatement(connection, CHECK_EMAIL_REQUEST_PREP , email);
			ResultSet resultat = statement.executeQuery();
			
			if (resultat.next()) {
				return  true;
			}
			
			DaoUtility.closeConnection(connection, statement, resultat);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
		
	}
*/
	public static User findUserByEmail(String email) {
		
		Connection connection = ConnectionManager.getConnection();
		try {
			
			PreparedStatement statement = DaoUtility.createStatement(connection, CHECK_EMAIL_REQUEST_PREP , email);
			ResultSet resultat = statement.executeQuery();
			
			if (resultat.next()) {
				
				User user = new User(resultat.getInt(1),resultat.getString(2),resultat.getString(3),resultat.getString(4),resultat.getString(5),resultat.getString(6),resultat.getString(7));
				
				return  user;
			}
			
			DaoUtility.closeConnection(connection, statement, resultat);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
}
	



	