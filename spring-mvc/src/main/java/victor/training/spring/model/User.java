package victor.training.spring.model;

public class User {
	private String username;
	private String fullName;
	

	public User(String username, String fullName) {
		this.username = username;
		this.fullName = fullName;
	}

	public final String getUsername() {
		return username;
	}

	public final void setUsername(String username) {
		this.username = username;
	}

	public final String getFullName() {
		return fullName;
	}

	public final void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
