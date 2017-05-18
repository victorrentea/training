package victor.training.spring.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
	private static Logger log = LoggerFactory.getLogger(UserService.class);

	private UserRequestContext userDetails;

	public String getUsername() {
		log.error("Here!! +++");
		return userDetails.getUsername();
	}
	
	public void loginUser(String username) {
		userDetails.setUsername(username);
		userDetails.setPremium(true); // Fake. Should retrieve user details from DB
	}

	public void setUserDetails(UserRequestContext userDetails) {
		this.userDetails = userDetails;
	}

}
