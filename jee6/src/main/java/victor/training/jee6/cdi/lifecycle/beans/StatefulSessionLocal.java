package victor.training.jee6.cdi.lifecycle.beans;

import java.io.Serializable;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;

@SessionScoped
@Stateful
public class StatefulSessionLocal implements Serializable {

	private static int NEXT_ID;
	private final int id = NEXT_ID++;

	public String getInstanceId() {
		return getClass().getSimpleName() + id + " ";
	}
}
