package victor.training.jee6.cdi.lifecycle.beans;

import java.io.Serializable;

import javax.ejb.Stateless;

@Stateless
public class StatelessLocal implements Serializable {

	private static int NEXT_ID;
	private final int id = NEXT_ID++;

	public String getInstanceId() {
		return getClass().getSimpleName() + id + " ";
	}
}
