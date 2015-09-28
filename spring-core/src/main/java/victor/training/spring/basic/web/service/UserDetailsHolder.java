package victor.training.spring.basic.web.service;

public interface UserDetailsHolder {

	String getUsername();

	void setUsername(String username);

	void setPremium(boolean premium);

	boolean isPremium();

}