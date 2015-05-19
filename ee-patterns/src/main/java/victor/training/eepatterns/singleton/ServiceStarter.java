package victor.training.eepatterns.singleton;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class ServiceStarter {

	@PostConstruct
	public void initResources() {
		System.out.println("Execute custom on-deploy initializations");
	}
}
