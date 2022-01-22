package utilityServices;


import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public class UtilityForm {
	
	public static Double getInputTypeDouble (HttpServletRequest request, Map<String, String> errors,  String parameter) {
		String pattern = "[0-9]+([\\.,][0-9]+)?";
		String value = request.getParameter(parameter);
		if (value != null && !value.trim().isEmpty() && value.matches(pattern)) {
			return Double.parseDouble(value);
		}
		errors.put(parameter, "La valeur saisie est incorrect");
		return null;
	}
	
	public static String getParameter(HttpServletRequest request, String parameter) {
		String value = request.getParameter(parameter);
		if (value == null || value.trim().isEmpty()) {
			return null;
		}
		return value.trim();
	}
	
	public static Part getPart(HttpServletRequest request, String partFileldName) throws IOException, ServletException {
		
		return request.getPart(partFileldName);
	}

	public static void checkFields(HttpServletRequest request, Map<String, String> errors, String... fields) {
		for (String field : fields) {
			if (getParameter(request, field) == null)
				errors.put(field, "Vous devez remplir ce champ");
		}

	}

	public static void checkImageFile( String fileName, InputStream fileContents, Map<String, String> errors, String fileFieldName ) {
        if ( fileName == null || fileContents == null ) {
            errors.put(fileFieldName, "Merci de sélectionner une image à envoyer." );
        }
    }
	
}
