package victor.training.java8.jprime.predicates;

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

	public boolean isNotInStock() {
		return status != Status.IN_STOCK;
	}
	
}
