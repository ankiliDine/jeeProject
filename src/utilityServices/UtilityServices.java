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
             * Il faut déterminer s'il s'agit bien d'un champ de type fichier :
             * on délègue cette opération à la méthode utilitaire
             * getFileName().
             */
            profilName = UtitlityFile.getFileName( part );

            /*
             * Si la méthode a renvoyé quelque chose, il s'agit donc d'un
             * champ de type fichier (input type="file").
             */
            if ( profilName != null && !profilName.isEmpty() ) {
                /*
                 * Antibug pour Internet Explorer, qui transmet pour une
                 * raison mystique le chemin du fichier local à la machine
                 * du client...
                 * 
                 * Ex : C:/dossier/sous-dossier/fichier.ext
                 * 
                 * On doit donc faire en sorte de ne sélectionner que le nom
                 * et l'extension du fichier, et de se débarrasser du
                 * superflu.
                 */
            	profilName = profilName.substring( profilName.lastIndexOf( '/' ) + 1 )
                        .substring( profilName.lastIndexOf( '\\' ) + 1 );

                /* Récupération du contenu du fichier */
            	profilContents = part.getInputStream();

            }
        } catch ( IllegalStateException e ) {
            /*
             * Exception retournée si la taille des données dépasse les limites
             * définies dans la section <multipart-config> de la déclaration de
             * notre servlet d'upload dans le fichier web.xml
             */
            e.printStackTrace();
            errors.put( PROFIL_FIELD, "Les données envoyées sont trop volumineuses." );
        } catch ( IOException e ) {
            /*
             * Exception retournée si une erreur au niveau des répertoires de
             * stockage survient (répertoire inexistant, droits d'accès
             * insuffisants, etc.)
             */
            e.printStackTrace();
            errors.put( PROFIL_FIELD, "Erreur de configuration du serveur." );
        } catch ( ServletException e ) {
            /*
             * Exception retournée si la requête n'est pas de type
             * multipart/form-data. Cela ne peut arriver que si l'utilisateur
             * essaie de contacter la servlet d'upload par un formulaire
             * différent de celui qu'on lui propose... pirate ! :|
             */
            e.printStackTrace();
            errors.put( PROFIL_FIELD,
                    "Ce type de requête n'est pas supporté, merci d'utiliser le formulaire prévu pour envoyer votre fichier." );
        }

        /* Si aucune erreur n'est survenue jusqu'à présent */
        if ( errors.isEmpty() ) {

            /* Validation du champ fichier. */
            try {
            	UtilityForm.checkImageFile(profilName, profilContents, errors, PROFIL_FIELD);
            } catch ( Exception e ) {
            	errors.put( PROFIL_FIELD, e.getMessage() );
            }
 
        }

        /* Si aucune erreur n'est survenue jusqu'à présent */
        if ( errors.isEmpty() ) {
            /* Écriture du fichier sur le disque */
            try {
                UtitlityFile.writeFile(profilContents, profilName, profilPath );
            } catch ( Exception e ) {
            	errors.put( PROFIL_FIELD, "Erreur lors de l'écriture du fichier sur le disque." );
            }
        }

        /* Initialisation du résultat global de la validation. */
        if ( errors.isEmpty() ) {
            return profilPath+profilName;
        } 
        return null;
	}
}
