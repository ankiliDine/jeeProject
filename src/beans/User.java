package beans;

public class User {

	int id;
	String name;
	String firstName;
	String email;
	String profil;
	String password;
	String function;
	
	
	public User() {
		super();
	}

	public User(String name, String firstName, String email, String profil, String password, String function) {
		
		this.name = name;
		this.firstName = firstName;
		this.email = email;
		this.profil = profil;
		this.password = password;
		this.function = function;
	}
	
	public User(int id, String name, String firstName, String email, String profil, String password, String function) {
		
		this(name, firstName, email, profil, password, function);
		this.id = id;
		
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getProfil() {
		return profil;
	}


	public void setProfil(String profil) {
		this.profil = profil;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getFunction() {
		return function;
	}


	public void setFunction(String function) {
		this.function = function;
	}
	
	
}
