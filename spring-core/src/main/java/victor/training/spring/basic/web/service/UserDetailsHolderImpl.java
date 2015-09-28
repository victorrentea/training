package victor.training.spring.basic.web.service;

public class UserDetailsHolderImpl implements UserDetailsHolder {

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
