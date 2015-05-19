package victor.training.jee6.cdi.lifecycle.beans;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RequestLocal {

	private static int NEXT_ID;
	private final int id = NEXT_ID++;

	public String getInstanceId() {
		return getClass().getSimpleName() + id + " ";
	}
}
