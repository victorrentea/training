package hello;

import java.io.Serializable;


public class UsernameContextPrincipal implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String username;
	private final AuthnContext context;

	public UsernameContextPrincipal(String username, AuthnContext context) {
		this.username = username;
		this.context = context;
	}

	public String getUsername() {
		return username;
	}

	public AuthnContext getContext() {
		return context;
	}
	
	
	
}
