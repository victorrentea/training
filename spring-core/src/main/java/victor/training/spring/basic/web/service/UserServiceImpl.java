package victor.training.spring.basic.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserServiceImpl implements UserService {
	private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	private UserDetailsHolder userDetails;

	public String getUsername() {
		log.error("Here!! +++");
		return userDetails.getUsername();
	}
	
	public void loginUser(String username) {
		userDetails.setUsername(username);
		userDetails.setPremium(true); // retrieve user details from DB
	}

	public void setUserDetails(UserDetailsHolder userDetails) {
		this.userDetails = userDetails;
	}

}
