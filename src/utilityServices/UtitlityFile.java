package utilityServices;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


import javax.servlet.http.Part;


public class UtitlityFile {
	public static final int TAILLE_TAMPON = 10240;
	/*
	 * Méthode utilitaire qui a pour but d'écrire le fichier passé en paramètre
	 * sur le disque, dans le répertoire donné et avec le nom donné.
	 */
	public static void writeFile(InputStream fileContents, String fileName, String path ) throws IOException {
	    /* Prépare les flux. */
	    BufferedInputStream input = null;
	    BufferedOutputStream output = null;
	    try {
	        /* Ouvre les flux. */
	    	input = new BufferedInputStream( fileContents, TAILLE_TAMPON );
	    	output = new BufferedOutputStream( new FileOutputStream( new File( path + fileName ) ),
	                TAILLE_TAMPON );

	        /*
	         * Lit le fichier reçu et écrit son contenu dans un fichier sur le
	         * disque.
	         */
	        byte[] tampon = new byte[TAILLE_TAMPON];
	        int longueur;
	        while ( ( longueur = input.read( tampon ) ) > 0 ) {
	            output.write( tampon, 0, longueur );
	        }
	    } finally {
	        try {
	            output.close();
	        } catch ( IOException ignore ) {
	        }
	        try {
	            input.close();
	        } catch ( IOException ignore ) {
	        }
	    }
	}
	/* 
	 * Méthode utilitaire qui a pour unique but d'analyser l'en-tête "content-disposition",
	 * et de vérifier si le paramètre "filename"  y est présent. Si oui, alors le champ traité
	 * est de type File et la méthode retourne son nom, sinon il s'agit d'un champ de formulaire 
	 * classique et la méthode retourne null. 
	 */
	public static String getFileName( Part part ) {
	    /* Boucle sur chacun des paramètres de l'en-tête "content-disposition". */
	    for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
	        /* Recherche de l'éventuelle présence du paramètre "filename". */
	        if ( contentDisposition.trim().startsWith("filename") ) {
	            /* Si "filename" est présent, alors renvoi de sa valeur, c'est-à-dire du nom de fichier. */
	            return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace("\"", "");
	        }
	    }
	    /* Et pour terminer, si rien n'a été trouvé... */
	    return null;
	}
/*
	public static String getFileName( Part part ) {
	        for ( String content : part.getHeader( "content-disposition" ).split( ";" ) ) {
	            if ( content.trim().startsWith( "filename" ) )
	                return content.substring( content.indexOf( "=" ) + 2, content.length() - 1 );
	        }
	        return "Default.file";
	}
*/	 
	public static String createFile(String filePath) {
		
		File file =  new File (filePath);
		
		if(file.exists()) {
			return null;
		} else {
			file.mkdirs();
			return file.getAbsolutePath();
		}
		
	}
/*
	final static String NEW_FORMAT = "yyyy/MM/dd";
	public static void main (String arg[]) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String dateStr = sdf.format(new Date());
		try {
			Date date = sdf.parse(dateStr);
			System.out.println(date);
		} catch (ParseException e1) {
			// 
			e1.printStackTrace();
		}
		
		/*
		String  userDirectory = "C:\\javaee\\eclipseWorkSpace\\wap\\WebContent\\UsersDirectory";
		String  owner = "anlim@gmail.com";
		userDirectory += "\\" + owner+"\\";
		//String p = createFile(userDirectory);
		if (createFile(userDirectory) == null)
			System.out.println("existe");
		
	}
*/
	public static String renameFile(String fileName, String newFileName) {
			
			File newFile = new File (newFileName);
			
			if (newFile.exists()) {
				return null;
			} else {
				File oldfile = new File(fileName);
				if(oldfile.renameTo(newFile))
						return newFile.getAbsolutePath();
			}
			
			return null;
	}
 
	public static boolean deleteFile(File f ) throws IOException{
			
		if(f.isDirectory()){
		//si le dossier est vide, supprimez-le
			if(f.list().length == 0){
	        	return f.delete();
	        }else{
	          //lister le contenu du répertoire
	        	String files[] = f.list();
		        for (String tmp : files) {
		        	File file = new File(f, tmp);
		             //suppression récursive
		            deleteFile(file);
		         }
	          //vérifiez à nouveau le dossier, s'il est vide, supprimez-le
		         if(f.list().length == 0){
		        	 return f.delete();
		         }
	          }
	      }else{
	          //si il est un fichier, supprimez-le
	          return f.delete();
	         
	      }
		return false;
	    }
	
}
