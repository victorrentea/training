package victor.first.domain;

import javax.validation.constraints.NotNull;

public class User {
	
	@NotNull
	private String username;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	private long id;
	public User() {
	}
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
	
	public final void setUsername(String username) {
		this.username = username;
	}
	public final void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public final void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", id=" + id
				+ "]";
	}
	
	 
	
}
