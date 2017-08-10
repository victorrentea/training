package victor.training.spring.model;

public class User {
	private final String username;
	private final String fullName;

	public User(String username, String fullName) {
		this.username = username;
		this.fullName = fullName;
	}

	public final String getUsername() {
		return username;
	}

	public final String getFullName() {
		return fullName;
	}

}
