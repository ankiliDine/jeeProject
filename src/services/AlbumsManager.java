package services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import beans.Album;
import beans.User;
import dao.AlbumDao;
import dao.UserDao;
import utilityServices.UtilityForm;
import utilityServices.UtilityServices;
import utilityServices.UtitlityFile;

public class AlbumsManager {
	
	private static final String ALBUM_ID = "idAlbum";
	private static final String ALBUM_NAME_FIELD = "albumName";
	private static final String NEW_ALBUM_NAME_FIELD = "newalbumName";
	private static final String THEME_FIELD = "theme";
	private static final String DETAIL_FIELD = "detail";
	private static final String VISIBILITY_FIELD = "visibility";
	private static final String EMAILS_USERS_FOR_CHARE = "emailsUsersForChare";
	private static final String ID_USERS_FOR_CHARE = "idUserForChar";
	
	private static final String     usersDirectory = "C:\\javaee\\UsersDirectory";
	
	private HttpServletRequest      request;
	private HashMap<String, String> errors;
	private Album                   album;
	private boolean                 status = false;
	private String 				    statusMessage = null;
	private ArrayList<User> usersForChare = new ArrayList<User>();
	
	public AlbumsManager (HttpServletRequest request) {
		
		this.request = request;
		errors = new HashMap <String, String> ();

	}

	public boolean albumManagement(User owner, String option) {

		String albumName = UtilityForm.getParameter(request, ALBUM_NAME_FIELD);
//...... debug
		System.out.println(albumName);
		UtilityForm.checkFields(request, errors, ALBUM_NAME_FIELD);
		
		if (option.equals("createAlbum") || option.equals("updateAlbum")) {
			
			String theme = UtilityForm.getParameter(request, THEME_FIELD);
			String detail =  UtilityForm.getParameter(request, DETAIL_FIELD);
			String visibility =  UtilityForm.getParameter(request, VISIBILITY_FIELD);
			
			UtilityForm.checkFields(request, errors, THEME_FIELD, DETAIL_FIELD, VISIBILITY_FIELD);
			
			album = new Album (albumName, theme, detail,visibility, owner);
		}
		
		int idAlbum = 0;
		if(option.equals("updateAlbum") || option.equals("deleteAlbum")) {
			idAlbum = Integer.parseInt(request.getParameter(ALBUM_ID));
			UtilityForm.checkFields(request, errors, ALBUM_ID);
		}
		
		if (!errors.isEmpty())
			return status;
			
		String userDirectory = usersDirectory+"\\" + owner.getEmail() + "\\";  
		String albumPath = userDirectory + albumName;
		
		switch (option) {
		
			case "createAlbum" :
				
				//albumPath = UtitlityFile.createFile(albumPath);

				if(UtitlityFile.createFile(albumPath) != null) {
					
					String albumProfil = UtilityServices.saveProfile(request, errors, albumPath);
					
					if (errors.isEmpty()) {
						album.setProfil(albumProfil);
						status = AlbumDao.addAlbum(album);
						if (status) {
							statusMessage = "L'album a été créer avec succès";
							System.out.println(album.getAlbumName());
						}
					} else {
						File file = new File(albumPath);
						try {
							UtitlityFile.deleteFile(file);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
						
				} else {
					errors.put(ALBUM_NAME_FIELD, "Désolé cet album existe déjà");
				}
					
				break;
				
			case "updateAlbum":
				String newAlbumName = UtilityForm.getParameter(request, NEW_ALBUM_NAME_FIELD);
				String newAlbumPath = userDirectory + newAlbumName;
				UtilityForm.checkFields(request, errors,NEW_ALBUM_NAME_FIELD);
				
				if (newAlbumPath != null && !albumPath.equals(newAlbumPath)) {
				
					newAlbumPath = UtitlityFile.renameFile(albumPath, newAlbumPath);
					
					if (newAlbumPath == null) {
		
						errors.put(NEW_ALBUM_NAME_FIELD, "Cet album existe déjà");
						
					}
					
				}
				
				if (!errors.isEmpty())
					return status;
				
				album.setIdAlbum(idAlbum);
				album.setAlbumName(newAlbumName);
				status = AlbumDao.updateAlbum(album);
				
				if (status) {
					statusMessage = "L'album a été modifier avec succès";
					album = AlbumDao.findAlbum(idAlbum);
				}
				break;
				
			case "deleteAlbum":
				File file = new File(albumPath);
				try {
					if(UtitlityFile.deleteFile(file)) {
//................ debug
						System.out.println("Supprimer");
						
						status = AlbumDao.deleteAlbum(idAlbum);
						if (status)
							statusMessage = "L'album a été supprimé avec succès";
					} else {
						statusMessage = "L'album non supprimé";
						//................ debug
						System.out.println(statusMessage);
					}
				} catch (IOException e) {
		
					e.printStackTrace();
					
				}
			default:
				break;
		}
		return status;	
			
	}

	public boolean chareAlbumManager() {
		
		String option = request.getServletPath().substring(1);
		UtilityForm.checkFields(request, errors, ALBUM_ID);
		if(!errors.isEmpty()) {
			statusMessage = "Aucun vos albums n'a été choisi pour le partage";
			return false;
		}
		album = AlbumDao.findAlbum(Integer.parseInt(request.getParameter(ALBUM_ID)));
		if("findUserForChar".equals(option)) {
			String emailsUsersForChare = UtilityForm.getParameter(request, EMAILS_USERS_FOR_CHARE);
			UtilityForm.checkFields(request, errors, EMAILS_USERS_FOR_CHARE);
			if(this.errors.isEmpty()) {
				
				for(String userEmail : emailsUsersForChare.split(";")){
					User user = UserDao.findUserByEmail(userEmail.trim());
					usersForChare.add(user);
				}
				
				return true;
			} else {
				this.statusMessage ="Vous devez reseigner les emails des utilisateurs à qui vous souhaitez parrtager cet Album";
				return status;
			}
		} 
		else if ("chareAlbum".equals(option)) {
		
			String [] idUserForCharStr = request.getParameterValues(ID_USERS_FOR_CHARE);
			UtilityForm.checkFields(request, errors, ID_USERS_FOR_CHARE);
			if(idUserForCharStr == null || idUserForCharStr.length == 0) {
				this.statusMessage = "Aucun utilisateur n'a été selection";
				return false;
			} else {
				
				if(errors.isEmpty()) {
					
					for(String idUserStr : idUserForCharStr) {
						if (idUserStr.matches("[0-9]+")) {
							status = AlbumDao.chareAlbum(album.getIdAlbum(), Integer.parseInt(idUserStr));
						} else {
							this.statusMessage = "Utilisateur selectionné innexistant";
							return false;
						}
					}
					
				}
			}
		}
		
		return status;
	}
	public Map<String, String> getErrors() {
		return errors;
	}
	
	public Album getAlbum() {
		return album;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public ArrayList<User> getUsersForChare() {
		return usersForChare;
	}
	
}
