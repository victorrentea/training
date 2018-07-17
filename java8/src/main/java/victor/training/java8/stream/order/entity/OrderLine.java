package victor.training.java8.stream.order.entity;

public class OrderLine {
	
	public enum Status {
		IN_STOCK, OUT_OF_STOCK, WAITING_FOR_STOCK
	}

	private Product product;
	private int count;
	private boolean specialOffer;
	private Status status;

	public OrderLine(Product product, int items) {
		this.product = product;
		this.count = items;
	}
	public OrderLine() {
	}
	
	public OrderLine(Status status) {
		this.status = status;
	}
	public boolean isSpecialOffer() {
		return specialOffer;
	}

	public OrderLine setSpecialOffer(boolean specialOffer) {
		this.specialOffer = specialOffer;
		return this;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public boolean wasDelivered() {
		return true;
	}
	
	
	
}
