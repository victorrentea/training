package victor.training.java8.cleanlambda.talk;

import java.util.Date;
import java.util.List;

public class Order {

	public enum Status {
		ACTIVE, INACTIVE, DRAFT
	}
	
	private Status status;

	public final Status getStatus() {
		return status;
	}

	public final void setStatus(Status status) {
		this.status = status;
	}
	
	public boolean isActive() {
		return status == Status.ACTIVE;
	}
	
	public Date getDeliveryDueDate() {
		return null;
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Customer getCustomer() {
		return null;
	}

	public List<OrderLine> getOrderLines() {
		// TODO Auto-generated method stub
		return null;
	}
}
