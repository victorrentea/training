package victor.first.domain;

public class UserService {
	public User getUserById() {
		return new User("jdoe", "John", "Doe", 13L);
	}
}
