package utilityServices;

import java.util.Base64;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import dao.UserDao;

public class UsersManagementUtility {
	
	public static void validerPassword(HttpServletRequest request, Map<String, String> erreurs, String CHAMP_PASSWORD,
			String CHAMP_PASSWORD_BIS) {
		
		String password = UtilityForm.getParameter(request, CHAMP_PASSWORD);
		String passwordBis = UtilityForm.getParameter(request, CHAMP_PASSWORD_BIS);
		
		if (password != null && !password.equals(passwordBis)) {
		
			erreurs.put(CHAMP_PASSWORD, "Les deux mots de passe ne sont pas identiques !");
			erreurs.put(CHAMP_PASSWORD_BIS, "Les deux mots de passe ne sont pas identiques !");
		
		}
	}

	public static void securePassword(HttpServletRequest request, Map<String, String> erreurs,String CHAMP_PASSWORD) {
		String password = UtilityForm.getParameter(request,CHAMP_PASSWORD);
//		String securePassword = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
		String chiffreRegex = "[0-9]";
		String lettreRegex = "[A-Za-z]";
		String specialcharRegex = "[^a-zA-Z0-9]";
		
		Pattern chiffrePattern = Pattern.compile(chiffreRegex);
		Pattern lettrePattern = Pattern.compile(lettreRegex);
		Pattern specialcharPattern = Pattern.compile(specialcharRegex);
		
		Matcher chiffreMatcher = chiffrePattern.matcher(password);
		Matcher lettreMatcher = lettrePattern.matcher(password);
		Matcher specialcharMatcher = specialcharPattern.matcher(password);
		
		String error = null;
//		Pattern pattern = Pattern.compile(securePassword);
		
		//------------debug
		System.out.println(password);
		//---------------debug
		
		if ( password == null || password.length() < 8 ) {
			error = "Le mot de passe doit contenir au moins 8 caractères ";
		} else if ( !chiffreMatcher.find()) {
			error = "Le mot de passe doit contenir au moins une chiffre ";
		} else if ( !lettreMatcher.find()) {
			error = "Le mot de passe doit contenir au moins un lettre ";
		} else if ( !specialcharMatcher.find()) {
			error = "Le mot de passe doit contenir au moins un autre des lettres et des chiffres et le _ ";
		} else if ( password.length() > 50 ) {
			error = "Mot de passe trop longue";
		}
		
		if ( error != null)
			erreurs.put(CHAMP_PASSWORD, error);
			
	}
	
	public static void emailIsValid(HttpServletRequest request, Map<String, String> erreurs,String CHAMP_EMAIL) {
		
		String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailPattern);
		String email = UtilityForm.getParameter(request,CHAMP_EMAIL);
//------------------debogage		
		System.out.println(email);
//------------------debogage			
		if (email == null || pattern.matcher(email).matches() == false) {
			erreurs.put(CHAMP_EMAIL, "cet email est invalide !");	
		} 
// utiliser findUserByEmail à lla place de checkEmail
		else if (UserDao.findUserByEmail(email) != null) {
			erreurs.put(CHAMP_EMAIL, "cet email est déjà utilisé !");
		}

	}

	public static String encode(String input) {
		String output = Base64.getEncoder().encodeToString(input.getBytes());
		return output;
	}
}
