package victor.training.java8.voxxed.order.entity;

public class OrderLine {
	
	public enum Status {
		IN_STOCK, OUT_OF_STOCK, WAITING_FOR_STOCK
	}

	private Product product;
	private int items;
	private boolean specialOffer;
	private Status status;

	public OrderLine(Product product, int items) {
		this.product = product;
		this.items = items;
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

	public int getItems() {
		return items;
	}

	public void setItems(int items) {
		this.items = items;
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
