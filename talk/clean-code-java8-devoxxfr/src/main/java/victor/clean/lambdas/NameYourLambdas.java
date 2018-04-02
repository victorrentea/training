package victor.clean.lambdas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@SuppressWarnings("unused")
public class NameYourLambdas {

	private UserRepo userRepo;
	
	private List<UserDto> getAllUsers() {
		List<User> users = userRepo.findAll();
		List<UserDto> dtos = new ArrayList<>();
		for (User user : users) {
			UserDto dto = new UserDto();
			dto.setFullName(user.getFirstName() + " " + user.getLastName().toUpperCase());
			dto.setUsername(user.getUsername());
			dto.setActive(user.getDeactivationDate() != null);
			dtos.add(dto);
		}
		return dtos;
	}
	
}

// -------- fake code ---------
interface UserRepo {
	List<User> findAll(); 
}

@Data
class User {
	private String firstName;
	private String lastName;
	private String username;
	private LocalDate deactivationDate;
}

@Data
class UserDto {
	private String fullName;
	private String username;
	private boolean active;
}
