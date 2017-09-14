package victor.first.domain;

public class User {
	private final String username, firstName, lastName;
	private long id;
	public User(String username, String firstName, String lastName, long id) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
	}
	public final long getId() {
		return id;
	}
	public final void setId(long id) {
		this.id = id;
	}
	public final String getUsername() {
		return username;
	}
	public final String getFirstName() {
		return firstName;
	}
	public final String getLastName() {
		return lastName;
	}
	 
	
}
