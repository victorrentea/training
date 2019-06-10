package ro.victor.unittest;

import java.time.LocalDateTime;

import org.junit.Test;

public class MutationProofTest {

	static class Customer {
		enum Status {
			ACTIVE
		}
		private Status status;
		private LocalDateTime activationTime;
		private long id;
		
		public void activate() {
			status = Status.ACTIVE;
			activationTime = LocalDateTime.now();
			publishEvent(new CustomerActivated(id));
		}

		private void publishEvent(CustomerActivated customerActivated) {
			 // TODO
		}
	}
	
	Customer customer = new Customer();
	
	@Test
	public void activate() {
		customer.activate();
	}
	
	static class CustomerActivated {

		public CustomerActivated(long id) {
		}}
	
}
