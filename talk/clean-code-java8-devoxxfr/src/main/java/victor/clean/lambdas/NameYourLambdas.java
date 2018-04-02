package victor.clean.lambdas;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@SuppressWarnings("unused")
public class NameYourLambdas {

	private UserRepo userRepo;
	
	private List<UserDto> getAllA() {
		List<A> users = userRepo.findAll();
		return users.stream()
			.map(user -> {
				UserDto userDto = new UserDto();
				userDto.setFullName(user.getFirstName() + " " + 
					user.getLastName().toUpperCase());
				userDto.setUsername(user.getUsername());
				userDto.setActive(user.getDeactivationDate() != null);
				return userDto;
			})
			.collect(toList());
	}
	
}

// -------- fake code ---------
interface UserRepo {
	List<A> findAll(); 
}

@Data
class A {
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
