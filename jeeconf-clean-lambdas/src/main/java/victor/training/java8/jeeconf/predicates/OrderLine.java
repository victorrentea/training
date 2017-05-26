package victor.training.java8.jeeconf.predicates;

public class OrderLine {

	public enum Status {
		IN_STOCK,
		AT_PROVIDER,
		UNAVAILABLE
	}
	
	private Status status;
	
	public Status getStatus() {
		return status;
	}
	
}
