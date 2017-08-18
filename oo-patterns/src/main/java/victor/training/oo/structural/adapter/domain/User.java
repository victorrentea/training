package victor.training.oo.structural.adapter.domain;

public class User {
	private String username;
	private String fullName;
	private String workEmail;
	
	public User(String username, String fullName, String workEmail) {
		this.username = username;
		this.fullName = fullName;
		this.workEmail = workEmail;
	}
	public final String getUsername() {
		return username;
	}
	public final String getFullName() {
		return fullName;
	}
	public final String getWorkEmail() {
		return workEmail;
	}
}
