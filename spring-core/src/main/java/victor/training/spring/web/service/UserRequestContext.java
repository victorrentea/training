package victor.training.spring.web.service;

public class UserRequestContext {

	private String username;
	private boolean premium;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public boolean isPremium() {
		return premium;
	}

}
