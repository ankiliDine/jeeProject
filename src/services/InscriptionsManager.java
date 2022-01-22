package services;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import beans.User;
import dao.UserDao;
import utilityServices.UtilityForm;
import utilityServices.EmailConfirmation;
import utilityServices.UsersManagementUtility;


public class InscriptionsManager {
	
	private static final String NAME_FIELD = "name";
	private static final String FIRST_NAME_FIELD_ = "firstName";
	private static final String EMAIL_FIELD = "email";
	//private static final String Field_PROFIL = "profil";
	private static final String PASSWORD_FIELD = "password";
	private static final String PASSWORD_BIS_FIELD = "passwordBis";
	//private static final String Field_FONCTION = "fonction";
	private static final String Field_CONFIRMATION_CODE = "confirmationCode";
	
	private HttpServletRequest request;
	private HashMap <String, String> errors;
	private User user;
	private String confirmationCode;


	public InscriptionsManager (HttpServletRequest request) {
		
		this.request = request;
		errors = new HashMap <String, String> ();

	}

	public boolean ValiderInscription() {

		String name = UtilityForm.getParameter(request,NAME_FIELD);
		String firstName =  UtilityForm.getParameter(request,FIRST_NAME_FIELD_);
		String email =  UtilityForm.getParameter(request,EMAIL_FIELD);
		String password =  UtilityForm.getParameter(request,PASSWORD_FIELD);
		//String passwordBis =  UtilityForm.getParameter(request,PASSWORD_BIS_FIELD);
		String profil =  "default";
		String fonction =  "utilisateur_simple";

		user = new User(name, firstName, email, profil, password, fonction);
		
		
		UtilityForm.checkFields(request,errors,NAME_FIELD,FIRST_NAME_FIELD_,EMAIL_FIELD,PASSWORD_FIELD,PASSWORD_BIS_FIELD);
		UsersManagementUtility.emailIsValid(request, errors,EMAIL_FIELD);
		
		if (password != null)
			UsersManagementUtility.securePassword(request, errors,PASSWORD_FIELD);
		
		UsersManagementUtility.validerPassword(request,errors,PASSWORD_FIELD,PASSWORD_BIS_FIELD);
		
		if(errors.isEmpty()) {
			//envoi de mail
			confirmationCode = EmailConfirmation.genererCode();
			
			//hachage du mot de passe
			user.setPassword(UsersManagementUtility.encode(user.getPassword()));
			
			if(EmailConfirmation.envoiEmail(user.getEmail(), confirmationCode)) {
				//le code a bien ete envoyé ou dumoins sil met un mail valide
				return true;
			}
			
		}
		
			//redirection page d'inscription avec les errors
		return false;

	}

	public boolean inscription ( User User, String confirmationCode) {
		
		String confirmationCodeBis = UtilityForm.getParameter(request,Field_CONFIRMATION_CODE);
//...............debug		
		System.out.println(confirmationCodeBis+" revoir "+confirmationCode);
		
		if (confirmationCode.equalsIgnoreCase(confirmationCodeBis)) {
			
			boolean status = UserDao.addUser(User);
			if (!status)
				this.errors.put(Field_CONFIRMATION_CODE, "Erreur lors du l'inscrion veillez verifier si toutes les informations que vous avez saisies sont correctes");
			
			return status;
		}
		
		this.errors.put(Field_CONFIRMATION_CODE,"Le code de confirmation que vous avez saisie est incorrecte veillez reéssayer de nouveau");
		return false;
	}
	
	
	public User getUser() {
		return user;
	}
	

	public Map<String, String> getErrors() {
		return errors;
	}


	public String getconfirmationCode() {
		return confirmationCode;
	}
}
