package victor.training.jee6.cdi.lifecycle.beans;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ApplicationLocal {

	private static int NEXT_ID;
	private final int id = NEXT_ID++;

	@Inject
	private RequestLocal requestLocal;

	public String getInstanceId() {
		return getClass().getSimpleName() + id + " ";
	}

	public String getInstanceIdOfRequestLocal() {
		return requestLocal.getInstanceId();
	}
}
