package ejb.container.security;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

@DeclareRoles({"ADMIN_ROLE", "USER_ROLE"})
@RolesAllowed("USER_ROLE")
@Stateless
public class MySecuredServiceBean implements MySecuredService {
	
	public void userAllowedMethod() {
	}
	
	@RolesAllowed("ADMIN_ROLE")
	public void adminAllowedMethod() {
		
	}
}
