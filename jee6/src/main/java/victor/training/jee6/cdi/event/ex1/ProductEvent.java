package victor.training.jee6.cdi.event.ex1;

import java.util.Date;

public class ProductEvent {
	private final long productId;
	private final Date eventDate;

	public ProductEvent(long productId, Date eventDate) {
		this.productId = productId;
		this.eventDate = eventDate;
	}

	public long getProductId() {
		return productId;
	}

	public Date getEventDate() {
		return eventDate;
	}

}
