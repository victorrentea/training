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
	public final boolean isSpecialOffer() {
		return specialOffer;
	}

	public final OrderLine setSpecialOffer(boolean specialOffer) {
		this.specialOffer = specialOffer;
		return this;
	}

	public final Product getProduct() {
		return product;
	}

	public final void setProduct(Product product) {
		this.product = product;
	}

	public final int getItems() {
		return items;
	}

	public final void setItems(int items) {
		this.items = items;
	}

	public final Status getStatus() {
		return status;
	}

	public final void setStatus(Status status) {
		this.status = status;
	}
	
	public boolean wasDelivered() {
		return true;
	}
	
	
	
}
