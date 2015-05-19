package victor.training.eepatterns.contextholder.thread;

import javax.inject.Inject;

import victor.training.eepatterns.contextholder.ContextKey;

/**
 * not real
 * @author VictorRentea
 *
 */
public class MyServlet {

	@Inject
	private MyFacade facade;

	public void processHTTPRequest() {
		ThreadLocalContextHolder.put(ContextKey.USERNAME_KEY, "Gica");
		facade.businessMethod();
	}
}
