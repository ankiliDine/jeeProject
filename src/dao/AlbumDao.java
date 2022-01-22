package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Album;
import beans.Image;
import beans.User;

public class AlbumDao {
	
	private static final String ADD_REQUEST_PREP = "INSERT INTO album (OWNER,THEME,NAME,DETAIL,VISIBILITY,PROFIL) values (?,?,?,?,?,?)";
	private static final String UPDATE_ALBUM_PREP = "UPDATE album SET THEME = ?, NAME = ?, DETAIL = ?,VISIBILITY= ? WHERE ID_ALBUM = ?";
	private static final String DELETE_REQUEST_PREP = "DELETE FROM album WHERE ID_ALBUM = ?";
	private static final String FIND_ALBUM_BY_ID_PREP = "SELECT *FROM album WHERE ID_ALBUM = ?";
//	private static final String FIND_ALBUM_BY_NAME_PREP = "SELECT *FROM album WHERE NAME = ?";
	private static final String FIND_ALBUM_BY_OWNER_PREP = "SELECT *FROM album WHERE OWNER = ?";
	private static final String CHARE_ALBUM_PREP = "INSERT INTO acces (ID_USER, ID_ALBUM) VALUES (?,?) ON DUPLICATE KEY UPDATE ID_USER = VALUES(ID_USER), ID_ALBUM = VALUES(ID_ALBUM)";
	private static final String CHARE_WITH_ME_PREP = "SELECT *FROM acces WHERE ID_USER = ?";
	private static final String FIND_PUBLIC_ALBUMS = "SELECT *FROM album WHERE VISIBILITY = 'public'";
	
	public static boolean addAlbum(Album album) {
		
		Connection connection = ConnectionManager.getConnection();
		
		try {
			
			PreparedStatement statement = DaoUtility.createStatement(connection, ADD_REQUEST_PREP,album.getOwner().getEmail(),album.getTheme(), album.getAlbumName(), album.getDetail(), album.getVisibility(), album.getProfil());
			int resultat = statement.executeUpdate();
			
			DaoUtility.closeConnection(connection, statement);
			
			if(resultat != 0) {
				return true;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return false;
		
	}

	public static Album findAlbum (int id_album) {
		
		Album album = null;
		Connection connection = ConnectionManager.getConnection();
		try {
			PreparedStatement statement = DaoUtility.createStatement(connection, FIND_ALBUM_BY_ID_PREP,id_album);
			ResultSet resultat = statement.executeQuery();
			
			if(resultat.next()) {
				
				int idAlbum = resultat.getInt("ID_ALBUM");
				String ownerEmail = resultat.getString("OWNER");
				String theme = resultat.getString("THEME");
				String name = resultat.getString("NAME");
				String detail = resultat.getString("DETAIL");
				String visibility = resultat.getString("VISIBILITY");
				
				User owner = UserDao.findUserByEmail(ownerEmail);
				
				album = new Album (idAlbum, name, theme, detail, visibility, owner);
				
				ArrayList <Image> albumImages = ImageDao.findAlbumImages(album);
				
				album.setAlbumImages(albumImages);
			}
			
			DaoUtility.closeConnection(connection, statement, resultat);
			
		} catch (SQLException e) {
		
			e.printStackTrace();
			
		}
		return album;
	}
	/*
	public static Album findAlbum (String nameAlbum) {
		
		Album album = null;
		Connection connection = ConnectionManager.getConnection();
		try {
			PreparedStatement statement = DaoUtility.createStatement(connection, FIND_ALBUM_BY_NAME_PREP, nameAlbum);
			ResultSet resultat = statement.executeQuery();
			
			if(resultat.next()) {
				
				int idAlbum = resultat.getInt("ID_ALBUM");
				String ownerEmail = resultat.getString("OWNER");
				String theme = resultat.getString("THEME");
				String name = resultat.getString("NAME");
				String detail = resultat.getString("DETAIL");
				String visibility = resultat.getString("VISIBILITY");
				
				User owner = UserDao.findUserByEmail(ownerEmail);
				
				album = new Album (idAlbum, name, theme, detail, visibility, owner);
			}
			
			DaoUtility.closeConnection(connection, statement, resultat);
			
		} catch (SQLException e) {
		
			e.printStackTrace();
			
		}
		return album;
	}
	*/
	public static ArrayList <Album> findUserAlbums (String userEmail) {
		ArrayList <Album> userAlbums = new ArrayList <Album>();
		Album album = null;
		Connection connection = ConnectionManager.getConnection();
		try {
			PreparedStatement statement = DaoUtility.createStatement(connection, FIND_ALBUM_BY_OWNER_PREP, userEmail);
			ResultSet resultat = statement.executeQuery();
			
			while(resultat.next()) {
				
				int idAlbum = resultat.getInt("ID_ALBUM");
				String ownerEmail = resultat.getString("OWNER");
				String theme = resultat.getString("THEME");
				String name = resultat.getString("NAME");
				String detail = resultat.getString("DETAIL");
				String visibility = resultat.getString("VISIBILITY");
				
				User albumOwner = UserDao.findUserByEmail(ownerEmail);
				
				album = new Album (idAlbum, name, theme, detail, visibility, albumOwner);
				userAlbums.add(album);
				
			}
			
			DaoUtility.closeConnection(connection, statement, resultat);
			return userAlbums;
		} catch (SQLException e) {
		
			e.printStackTrace();
			
		}
		return userAlbums;
	}
	public static boolean updateAlbum(Album album) {
		
		Connection connection = ConnectionManager.getConnection();
		
		try {
			PreparedStatement statement = DaoUtility.createStatement(connection, UPDATE_ALBUM_PREP, album.getTheme(),album.getAlbumName(), album.getDetail(), album.getVisibility(), album.getIdAlbum());
			int resultat = statement.executeUpdate();
			DaoUtility.closeConnection(connection, statement);
			if(resultat != 0) {
				return true;
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
			
		}
	
		return false;
	}

	public static boolean deleteAlbum(int idAlbum) {
	
		Connection connection = ConnectionManager.getConnection();
		
		try {
			
			PreparedStatement statement = DaoUtility.createStatement(connection, DELETE_REQUEST_PREP, idAlbum); 
		
			int resultat = statement.executeUpdate();
			DaoUtility.closeConnection(connection, statement);
			if(resultat != 0) {
				return true;
			}
			
		} catch (SQLException e) {
	
			e.printStackTrace();
			
		}
		
		return false;
	}

	public static boolean chareAlbum(int idAlbum, int  idUserForChare) {
		Connection connection = ConnectionManager.getConnection();

			try {

				PreparedStatement statement = DaoUtility.createStatement(connection, CHARE_ALBUM_PREP, idUserForChare, idAlbum);
				int resultat = statement.executeUpdate();	
				DaoUtility.closeConnection(connection, statement);
				if (resultat != 0) {
					return true;
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
	
		return false;
	}

	public static ArrayList<Album> findCharedAlbums(int idUser) {
		
		ArrayList<Album> charedAlbums = new ArrayList<Album> ();
		Connection connection = ConnectionManager.getConnection();
		
		try {
			PreparedStatement statement = DaoUtility.createStatement(connection, CHARE_WITH_ME_PREP, idUser);
			ResultSet resultat = statement.executeQuery();
			System.out.println(idUser+" idu ");
			while(resultat.next()) {
				int idAlbum = resultat.getInt("ID_ALBUM");
			
				Album album = findAlbum (idAlbum);
				charedAlbums.add(album);
			}
			DaoUtility.closeConnection(connection, statement, resultat);
			return charedAlbums;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<Album> findPublicAlbums() {
		ArrayList<Album> publicAlbums = new ArrayList<Album> ();
		Connection connection = ConnectionManager.getConnection();
	
		try {
			PreparedStatement statement = DaoUtility.createStatement(connection, FIND_PUBLIC_ALBUMS);
			ResultSet resultat = statement.executeQuery();
			
			while(resultat.next()) {
				
				int idAlbum = resultat.getInt("ID_ALBUM");
				String ownerEmail = resultat.getString("OWNER");
				String theme = resultat.getString("THEME");
				String name = resultat.getString("NAME");
				String detail = resultat.getString("DETAIL");
				String visibility = resultat.getString("VISIBILITY");
				
				User owner = UserDao.findUserByEmail(ownerEmail);
				
				Album album = new Album (idAlbum, name, theme, detail, visibility, owner);
				
				ArrayList <Image> albumImages = ImageDao.findAlbumImages(album);
				
				album.setAlbumImages(albumImages);
				
				publicAlbums.add(album);
			}
			
			DaoUtility.closeConnection(connection, statement, resultat);
			return publicAlbums;
		} catch (SQLException e) {
		
			e.printStackTrace();
			
		}
		return null;
	}
	
	
}