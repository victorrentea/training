package victor.training.java8.functionalpatterns;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// get the list of users to UI

class UserFacade {
	
	private UserRepo userRepo;
	
	public List<UserDto> getAllUsers() {
		List<User> users = userRepo.findAll();
		List<UserDto> dtos = new ArrayList<>();
		for (User user : users) {
			UserDto dto = new UserDto();
			dto.setUsername(user.getUsername());
			dto.setFullName(user.getFirstName() + " " + user.getLastName().toUpperCase());
			dto.setActive(user.getDeactivationDate() == null);
			dtos.add(dto);
		}
		return dtos;
	}
}










// VVVVVVVVV ==== supporting (dummy) code ==== VVVVVVVVV
interface UserRepo {
	List<User> findAll();
}

class User {
	private String firstName;
	private String lastName;
	private String username;
	private LocalDate deactivationDate;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public LocalDate getDeactivationDate() {
		return deactivationDate;
	}
	public void setDeactivationDate(LocalDate deactivationDate) {
		this.deactivationDate = deactivationDate;
	}
	
}

class UserDto {
	private String fullName;
	private String username;
	private boolean active;
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
}
