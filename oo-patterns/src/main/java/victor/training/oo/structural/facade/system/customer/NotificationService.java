package victor.training.oo.structural.facade.system.customer;

public class NotificationService {
	public void sendActivationLink(Customer customer) {
		System.out.println("Sending activation link via email to "+ customer.getName());
	}
}
