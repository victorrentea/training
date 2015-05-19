package victor.training.eepatterns.soa.customer.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import victor.training.eepatterns.soa.customer.domain.Customer;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class RegisterCustomer {

	public void registerCustomer(Customer customer) {
		// implement reusable, coesive and fine-grained logic
	}
}
