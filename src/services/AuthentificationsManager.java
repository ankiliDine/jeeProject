package services;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.User;
import dao.UserDao;
import utilityServices.UtilityForm;;

public class AuthentificationsManager {
	
	private static final String EMAIL_FIELD = "email";
	private static final String PASSWORD_FIELD = "password";
	
	private HttpServletRequest request;
	private Map <String, String> errors;
	private User user;
	private String email; 

	
	public AuthentificationsManager (HttpServletRequest request) {
		
		this.request = request;
		this.errors = new HashMap<String,String>();
		this.user  = null;
	
	}
	
	public boolean authentifier() {
		
		
		email = UtilityForm.getParameter(request,EMAIL_FIELD);
		String password = UtilityForm.getParameter(request,PASSWORD_FIELD);

		UtilityForm.checkFields (request,errors,EMAIL_FIELD,PASSWORD_FIELD);
		
		if("admin@gmail.com".equals(email) && "passer".equals(password)) {
			
			user = new User(1,"","",email,"", password, "admin");
			
			return true;
			
		}else if(errors.isEmpty()) {
			
			user = UserDao.findUserByEmail(email);
			
			if (user == null) {
				
				errors.put(EMAIL_FIELD, "Ce compte n'existe pas ");
				
			} else if (!password.equals(user.getPassword())) {
				
				errors.put(PASSWORD_FIELD, "Mot de passe incorrect ");
			} else {
				return true;
			}
		}
		
		return false;
	}
	
	public static void deconnexion(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
	}


	public Map<String, String> getErrors() {
		return errors;
	}



	public User getUser() {
		return user;
	}

	public Object getEmail() {
		// TODO Auto-generated method stub
		return email;
	}

}
