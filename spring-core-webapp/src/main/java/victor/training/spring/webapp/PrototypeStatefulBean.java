package victor.training.spring.webapp;

public class PrototypeStatefulBean {

	private String state;

	public final String getState() {
		return state;
	}

	public final void setState(String state) {
		this.state = state;
	}
}
