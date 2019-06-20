package hello;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class DatabaseUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {


	private static final Logger log = LoggerFactory.getLogger(DatabaseUserDetailsService.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
		UsernameContextPrincipal principal = (UsernameContextPrincipal) token.getPrincipal();

		String username = principal.getUsername();
		if (StringUtils.isBlank(username)) {
			throw new UsernameNotFoundException("No username provided");
		}

		log.debug("Lookup username {} in database", username);
		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("User " + username + " not in database");
		} else {
			log.debug("User found in database");
			if (!user.isEnabled()) {
				throw new UsernameNotFoundException("User is inactive in the database");
			}
		}

		log.debug("Allowing the request to get in");
//		return new MyUserWithContext<User>(user.getUsername(), user, principal.getContext());
		return user;
	}


}
