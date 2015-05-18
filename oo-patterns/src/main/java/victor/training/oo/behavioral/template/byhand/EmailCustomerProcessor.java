package victor.training.oo.behavioral.template.byhand;

public class EmailCustomerProcessor extends AbstractCustomerProcessor {

	@Override
	protected void notifyCustomerOfFailure(Customer customer) {
		System.out.println("Send an email announcing the failure of his registration");
	}

	// optional
	@Override
	protected void notifyCustomerOfSuccess(Customer customer) {
		System.out.println("Also send an email to notify the registration success");
	}
}
