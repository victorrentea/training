package victor.training.java8.cleanlambda.talk;

public class OrderLine {
	
	public enum Status {
		IN_STOCK,
		IN_SUPPLIER_STOCK,
		OUT_OF_STOCK, ORDERED_FROM_SUPPLIER
	}

	public int getQuantity() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public boolean isPromo() {
		return true;
	}

	public Status getStatus() {
		return null;
	}
	
	public boolean isNotInStock() {
		return true;
	}
}
