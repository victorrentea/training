package victor.training.jee6.ejb.singleton;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class ServiceStarter {

	@PostConstruct
	public void initializeApplication() {
		System.out.println("Do stuff when the application is deployed");
	}
}
