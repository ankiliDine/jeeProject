package services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import beans.Album;
import beans.Image;
import dao.AlbumDao;
import dao.ImageDao;
import utilityServices.UtilityForm;
import utilityServices.UtilityServices;
import utilityServices.UtitlityFile;

public class ImagesManager {
	
	private static final String ALBUM_ID			 = "idAlbum";
	private static final String IMAGE_ID			 = "idImage";
	private static final String IMAGE_TITLE_FIELD    = "title";
	private static final String DESCRIPTION_FIELD    = "description";
	private static final String CREATION_DATE_FIELD	 = "creationDate";
	private static final String HEIGHT_FIELD         = "height";
	private static final String WIDTH_FIELD          = "width";
	private static final String KEYWORDS_FIELD       = "keyWords";
	private static final String IMAGE_FILE   		 = "image";
	private static final String ACCESSIBILITY_FIELD  = "accessibility";
	
	private static final String     usersDirectory = "C:\\javaee\\UsersDirectory";
	private HttpServletRequest      request;
	private HashMap<String, String> errors;
	private Image					image;
	private	Album 					imageAlbum;
	private boolean                 status = false;
	private String 				    statusMessage = null;
	
	public ImagesManager (HttpServletRequest request) {
		
		this.request = request;
		errors = new HashMap <String, String> ();

	}
	
	public boolean ImageManagement(String option) {
	
		String idAlbum = UtilityForm.getParameter(request, ALBUM_ID);
		if(idAlbum == null || !idAlbum.matches("[0-9]+")) {
			statusMessage = "L'abum choisis n'existe pas";
			return status;
		}
		
		imageAlbum = AlbumDao.findAlbum(Integer.parseInt(idAlbum));
		if(imageAlbum == null || !UtilityServices.isOwner(request, imageAlbum.getOwner().getEmail())) {
			statusMessage = "L'abum choisis n'existe pas dans votre galerie";
			return status;
		}
	
		String imagePath = usersDirectory + "\\" + imageAlbum.getOwner().getEmail() + "\\" + imageAlbum.getAlbumName() + "\\";
		String title = null ;
		if (option.equals("createImage") || option.equals("updateImage")) {
			title 			= UtilityForm.getParameter(request, IMAGE_TITLE_FIELD);
			String description 		= UtilityForm.getParameter(request, DESCRIPTION_FIELD);
			String creationDateStr 	= UtilityForm.getParameter(request, CREATION_DATE_FIELD);
			Double height 			= UtilityForm.getInputTypeDouble(request, errors, HEIGHT_FIELD);
			Double width			= UtilityForm.getInputTypeDouble(request, errors, WIDTH_FIELD);
			String keyWords			= UtilityForm.getParameter(request, KEYWORDS_FIELD);
			String accessibility	= UtilityForm.getParameter(request, ACCESSIBILITY_FIELD);
			System.out.print(height + " // " +width);
			Date creationDate = null, updateDate = null;
			
	
				try {
					creationDate = new SimpleDateFormat("YYYY-MM-DD").parse(creationDateStr);
					updateDate = creationDate;
				} catch (ParseException e) {
					errors.put(CREATION_DATE_FIELD, "Le format de la date est invalide");
				} catch (NullPointerException n) {
					errors.put(CREATION_DATE_FIELD, "Aucun daate n'a été renseigner");
				}
			
			
			
			image = new Image ( title,  description, creationDate, updateDate,  height,
					 width,  keyWords,  accessibility,  imagePath, imageAlbum);
			
			UtilityForm.checkFields(request, errors, IMAGE_TITLE_FIELD, DESCRIPTION_FIELD, CREATION_DATE_FIELD, HEIGHT_FIELD, WIDTH_FIELD, KEYWORDS_FIELD, ACCESSIBILITY_FIELD);
		}
		
		int idImage = 0;
		if(option.equals("updateImage") || option.equals("deleteImage")) {
			idImage = Integer.parseInt(request.getParameter(IMAGE_ID));
			UtilityForm.checkFields(request, errors, IMAGE_ID);
		}
		
		if(!errors.isEmpty()) {
			return status;
		}

		switch (option) {
		
			case "createImage" :
			
			InputStream fileContents = null;
			String fileName  = null;
			try {
				Part part = request.getPart( IMAGE_FILE );
				fileName = UtitlityFile.getFileName( part );
				 if ( fileName != null && !fileName.isEmpty() ) {
		               
					 fileName = fileName.substring( fileName.lastIndexOf( '/' ) + 1 )
		                        .substring( fileName.lastIndexOf( '\\' ) + 1 );

		                /* Récupération du contenu du fichier */
		             fileContents = part.getInputStream();

		            }
			} catch ( IllegalStateException e ) {
	            /*
	             * Exception retournée si la taille des données dépasse les limites
	             * définies dans la section <multipart-config> de la déclaration de
	             * notre servlet imageOpServlet 
	             */
	            e.printStackTrace();
	            errors.put( IMAGE_FILE, "Les données envoyées sont trop volumineuses." );
	        } catch ( IOException e ) {
	            /*
	             * Exception retournée si une erreur au niveau des répertoires de
	             * stockage survient (répertoire inexistant, droits d'accès
	             * insuffisants, etc.)
	             */
	            e.printStackTrace();
	            //errors.put( IMAGE_FILE, "Erreur de configuration du serveur." );
	        } catch ( ServletException e ) {
	            /*
	             * Exception retournée si la requête n'est pas de type
	             * multipart/form-data. Cela ne peut arriver que si l'utilisateur
	             * essaie de contacter la servlet imageOpServlet par un formulaire
	             * différent de celui qu'on lui propose... pirate ! :|
	             */
	            e.printStackTrace();
	            statusMessage = 
	                    "Ce type de requête n'est pas supporté, merci d'utiliser le formulaire prévu pour envoyer votre fichier.";
	        }
			
			UtilityForm.checkImageFile(fileName, fileContents, errors, IMAGE_FILE );
			 /* Si aucune erreur n'est survenue jusqu'à présent */
	        if ( errors.isEmpty() ) {
	            /* Écriture du fichier sur le disque */
	            try {
	            	
	            	image.setImagePath(image.getImagePath()+fileName);
	            	UtitlityFile.writeFile( fileContents, fileName, imagePath );
	            	status = ImageDao.addImage(image);
	            	
	            	if(status) {
	            		imageAlbum.setAlbumImages(image);
	            	}
	            } catch ( Exception e ) {
	                statusMessage = "Erreur lors de l'écriture du fichier sur le disque." ;
	            }
	        }
					
				break;
				
			case "updateImage":
				image.setIdImage(idImage);
				status = ImageDao.updateImage(image);
				
				if (status) {
					statusMessage = "L'image a été modifier avec succès";
					imageAlbum = AlbumDao.findAlbum(Integer.parseInt(idAlbum));
				}
				break;
				
			case "deleteAlbum":
				File file = new File(imagePath);
				try {
					if(UtitlityFile.deleteFile(file)) {
//................ debug
						System.out.println("Supprimer");
						
						status = ImageDao.deleteImage(idImage);
						if (status)
							statusMessage = "L'image a été supprimé avec succès";
					} else {
						statusMessage = "L'image non supprimé";
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

	public HashMap<String, String> getErrors() {
		return errors;
	}


	public Image getImage() {
		return image;
	}

	public boolean isStatus() {
		return status;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public Album getImageAlbum() {
		return imageAlbum;
	}


}
