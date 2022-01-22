package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import beans.Album;
import beans.Image;

public class ImageDao {
	
	private static final String ADD_REQUEST_PREP = "INSERT INTO image (ID_ALBUM,TITLE,DESCRIPTION,HEIGHT,UPDATE_DATE,CREATION_DATE,WIDTH,ACCESSIBILITY,IMAGE_PATH,KEYWORDS) values (?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_IMAGE_PREP = "UPDATE image SET TITLE = ?, DESCRIPTION = ?, HEIGHT = ?,UPDATE_DATE = ?,WIDTH = ?,ACCESSIBILITY= ?,IMAGE_PATH = ?,KEYWORDS = ? WHERE ID_IMAGE = ?";
	private static final String DELETE_REQUEST_PREP = "DELETE FROM image WHERE ID_IMAGE = ?";
	private static final String FIND_IMAGE_BY_ID_PREP = "SELECT *FROM image WHERE ID_IMAGE = ?";
	private static final String FIND_ALBUM_IMAGES_PREP = "SELECT *FROM image WHERE ID_ALBUM = ?";
	//private static final String FIND_IMAGES_BY_ALBUM_PREP = "SELECT *FROM image WHERE ID_ALBUM = ?";
	
	
	public static boolean addImage(Image image) {
		
		Connection connection = ConnectionManager.getConnection();
		
		try {

			PreparedStatement statement = DaoUtility.createStatement(connection, ADD_REQUEST_PREP,image.getAlbum().getIdAlbum(),image.getTitle(),image.getDescription(),image.getHeight(),image.getUpdateDate(),image.getCreationDate(),image.getWidth(),image.getAccessibility(),image.getImagePath(),image.getKeyWords());
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
	
	public static Image findImage (int idImage) {
		
		Image image = null;
		Connection connection = ConnectionManager.getConnection();
		try {
			PreparedStatement statement = DaoUtility.createStatement(connection, FIND_IMAGE_BY_ID_PREP,idImage);
			ResultSet resultat = statement.executeQuery();
			
			if(resultat.next()) {
				
				idImage = resultat.getInt("ID_IMAGE");
				int idAlbum = resultat.getInt("ID_ALBUM");
				String title = resultat.getString("TITLE");
				String description = resultat.getString("DESCRIPTION");
				Double heigth = resultat.getDouble("HEIGHT");
				Date updateDate = resultat.getDate("UPDATE_DATE");
				Date creationDate = resultat.getDate("CREATION_DATE");
				Double width = resultat.getDouble("WIDTH");
				String accessibility = resultat.getString("ACCESSIBILITY");
				String imagePath = resultat.getString("IMAGE_PATH");
				String keyWords = resultat.getString("KEYWORDS");
				
				Album album = AlbumDao.findAlbum(idAlbum);
				image = new Image(idImage,  title,  description,  creationDate, updateDate,  heigth,
						 width,  keyWords,  accessibility,  imagePath,  album);
			}
			
			DaoUtility.closeConnection(connection, statement, resultat);
			
		} catch (SQLException e) {
		
			e.printStackTrace();
			
		}
		return image;
	}
	
	public static boolean updateImage(Image image) {
		
		Connection connection = ConnectionManager.getConnection();
		
		try {
			PreparedStatement statement = DaoUtility.createStatement(connection, UPDATE_IMAGE_PREP, image.getTitle(), image.getDescription(), image.getHeight(), new Date(), image.getWidth(), image.getAccessibility(), image.getImagePath(), image.getKeyWords(), image.getIdImage() );
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

	public static boolean deleteImage(int idImage) {
	
		Connection connection = ConnectionManager.getConnection();
		
		try {
			
			PreparedStatement statement = DaoUtility.createStatement(connection, DELETE_REQUEST_PREP, idImage); 
		
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

	public static ArrayList<Image> findAlbumImages(Album album) {
		
		ArrayList<Image> albumImages = new ArrayList <Image> ();
		
		Connection connection = ConnectionManager.getConnection();
		
		try {
			PreparedStatement statement = DaoUtility.createStatement(connection, FIND_ALBUM_IMAGES_PREP, album.getIdAlbum());
			ResultSet resultat = statement.executeQuery();
			
			while(resultat.next()) {
				
				int idImage = resultat.getInt("ID_IMAGE");
				String title = resultat.getString("TITLE");
				String description = resultat.getString("DESCRIPTION");
				Double heigth = resultat.getDouble("HEIGHT");
				Date updateDate = resultat.getDate("UPDATE_DATE");
				Date creationDate = resultat.getDate("CREATION_DATE");
				Double width = resultat.getDouble("WIDTH");
				String accessibility = resultat.getString("ACCESSIBILITY");
				String imagePath = resultat.getString("IMAGE_PATH");
				String keyWords = resultat.getString("KEYWORDS");
				
				
				Image image = new Image(idImage,  title,  description,  creationDate, updateDate,  heigth,
						 width,  keyWords,  accessibility,  imagePath,  album);
				
				albumImages.add(image);
			}
			DaoUtility.closeConnection(connection, statement, resultat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return albumImages;
	}
	
	
}