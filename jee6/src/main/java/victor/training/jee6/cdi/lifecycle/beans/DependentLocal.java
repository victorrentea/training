package victor.training.jee6.cdi.lifecycle.beans;


public class DependentLocal {

	private static int NEXT_ID;
	private final int id = NEXT_ID++;

	public String getInstanceId() {
		return getClass().getSimpleName() + id + " ";
	}
}
