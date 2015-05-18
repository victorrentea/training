package ejb.v31;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class SingletonBean {
	@PostConstruct
	public void init() {
		System.out.println("Created Singleton EJB");
		System.out.println(" ============================================================= ");
	}

}
