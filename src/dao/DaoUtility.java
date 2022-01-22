package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoUtility{

	static PreparedStatement createStatement (Connection connection,String request,Object ...objects) throws SQLException {
		
		PreparedStatement statement = connection.prepareStatement(request);
		
		for(int i=1; i<= objects.length; i++) {
			statement.setObject(i, objects[--i]);
			i++;
		}
		
		return statement;
	}
	
	static void closeConnection (Connection connection, Statement statement) {
		
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	static void closeConnection (Connection connection, Statement statement, ResultSet resultat) {
		
		if (resultat != null) {
			try {
				resultat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		closeConnection (connection,statement);
		
	}
}