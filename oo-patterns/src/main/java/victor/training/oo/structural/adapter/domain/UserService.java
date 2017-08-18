package victor.training.oo.structural.adapter.domain;

import java.util.ArrayList;
import java.util.List;

import victor.training.oo.structural.adapter.external.LdapUser;
import victor.training.oo.structural.adapter.external.LdapUserWebserviceClient;

public class UserService {
	private LdapUserWebserviceClient wsClient;
	
	public UserService(LdapUserWebserviceClient wsClient) {
		this.wsClient = wsClient;
	}

	public void importUserFromLdap(String username) {
		List<LdapUser> list = wsClient.search(username, null, null);
		if (list.size() != 1) {
			throw new IllegalArgumentException("There is no user with username " + username);
		}
		LdapUser theLdapUser = list.get(0);
		String fullName = theLdapUser.getfName() + " " + theLdapUser.getlName().toUpperCase();
		User user = new User(username, fullName, theLdapUser.getWorkEmail());
		
		if (user.getWorkEmail() != null) {
			System.out.println("Send welcome email to " + user.getWorkEmail());
		}
		System.out.println("Insert user in my database");
	}
	
	public List<User> searchUserInLdap(String username) {
		List<LdapUser> list = wsClient.search(username, null, null);
		List<User> results = new ArrayList<>();
		for (LdapUser ldapUser : list) {
			String fullName = ldapUser.getfName() + " " + ldapUser.getlName().toUpperCase();
			User user = new User(username, fullName, ldapUser.getWorkEmail());
			results.add(user);
		}
		return results;
	}
	
	public static void main(String[] args) {
		UserService service = new UserService(new LdapUserWebserviceClient());
		System.out.println(service.searchUserInLdap("doe"));
		service.importUserFromLdap("jdoe");
	}
}
