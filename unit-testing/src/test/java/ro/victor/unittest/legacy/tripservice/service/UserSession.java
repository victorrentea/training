package ro.victor.unittest.legacy.tripservice.service;


import ro.victor.unittest.legacy.tripservice.exception.ShouldBeMockedAwayException;
import ro.victor.unittest.legacy.tripservice.model.User;

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
