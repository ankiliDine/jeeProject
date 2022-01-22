package utilityServices;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import beans.User;

public class UtilityServices {
	private static final String PROFIL_FIELD = "profil";

	public static boolean isOwner (HttpServletRequest request , String albumOwnerEmail) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user.getEmail().equals(albumOwnerEmail) )
			return true;
		return false;
	}
	
	public static String saveProfile (HttpServletRequest request, HashMap<String, String> errors, String profilPath) {
	
        String profilName = null;
        InputStream profilContents = null;
        try {
            Part part = request.getPart( PROFIL_FIELD );
            /*
             * Il faut d�terminer s'il s'agit bien d'un champ de type fichier :
             * on d�l�gue cette op�ration � la m�thode utilitaire
             * getFileName().
             */
            profilName = UtitlityFile.getFileName( part );

            /*
             * Si la m�thode a renvoy� quelque chose, il s'agit donc d'un
             * champ de type fichier (input type="file").
             */
            if ( profilName != null && !profilName.isEmpty() ) {
                /*
                 * Antibug pour Internet Explorer, qui transmet pour une
                 * raison mystique le chemin du fichier local � la machine
                 * du client...
                 * 
                 * Ex : C:/dossier/sous-dossier/fichier.ext
                 * 
                 * On doit donc faire en sorte de ne s�lectionner que le nom
                 * et l'extension du fichier, et de se d�barrasser du
                 * superflu.
                 */
            	profilName = profilName.substring( profilName.lastIndexOf( '/' ) + 1 )
                        .substring( profilName.lastIndexOf( '\\' ) + 1 );

                /* R�cup�ration du contenu du fichier */
            	profilContents = part.getInputStream();

            }
        } catch ( IllegalStateException e ) {
            /*
             * Exception retourn�e si la taille des donn�es d�passe les limites
             * d�finies dans la section <multipart-config> de la d�claration de
             * notre servlet d'upload dans le fichier web.xml
             */
            e.printStackTrace();
            errors.put( PROFIL_FIELD, "Les donn�es envoy�es sont trop volumineuses." );
        } catch ( IOException e ) {
            /*
             * Exception retourn�e si une erreur au niveau des r�pertoires de
             * stockage survient (r�pertoire inexistant, droits d'acc�s
             * insuffisants, etc.)
             */
            e.printStackTrace();
            errors.put( PROFIL_FIELD, "Erreur de configuration du serveur." );
        } catch ( ServletException e ) {
            /*
             * Exception retourn�e si la requ�te n'est pas de type
             * multipart/form-data. Cela ne peut arriver que si l'utilisateur
             * essaie de contacter la servlet d'upload par un formulaire
             * diff�rent de celui qu'on lui propose... pirate ! :|
             */
            e.printStackTrace();
            errors.put( PROFIL_FIELD,
                    "Ce type de requ�te n'est pas support�, merci d'utiliser le formulaire pr�vu pour envoyer votre fichier." );
        }

        /* Si aucune erreur n'est survenue jusqu'� pr�sent */
        if ( errors.isEmpty() ) {

            /* Validation du champ fichier. */
            try {
            	UtilityForm.checkImageFile(profilName, profilContents, errors, PROFIL_FIELD);
            } catch ( Exception e ) {
            	errors.put( PROFIL_FIELD, e.getMessage() );
            }
 
        }

        /* Si aucune erreur n'est survenue jusqu'� pr�sent */
        if ( errors.isEmpty() ) {
            /* �criture du fichier sur le disque */
            try {
                UtitlityFile.writeFile(profilContents, profilName, profilPath );
            } catch ( Exception e ) {
            	errors.put( PROFIL_FIELD, "Erreur lors de l'�criture du fichier sur le disque." );
            }
        }

        /* Initialisation du r�sultat global de la validation. */
        if ( errors.isEmpty() ) {
            return profilPath+profilName;
        } 
        return null;
	}
}
