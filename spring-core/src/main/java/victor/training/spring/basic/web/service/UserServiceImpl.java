package victor.training.spring.basic.web.service;

public class UserServiceImpl implements UserService {

	private UserDetailsHolder userDetails;

	public String getUsername() {
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
