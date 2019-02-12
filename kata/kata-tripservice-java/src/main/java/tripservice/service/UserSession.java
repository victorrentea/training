package tripservice.service;

import tripservice.exception.ShouldBeMockedAwayException;
import tripservice.model.User;

public class UserSession {
	private static UserSession INSTANCE;
	
	public static UserSession getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UserSession();
		}
		return INSTANCE;
	}

	public User getLoggedUser() {
		throw new ShouldBeMockedAwayException("Depends on infra stuff");
	}
	
	
}
