package victor.training.eepatterns.soa.customer.facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import victor.training.eepatterns.soa.customer.domain.Customer;
import victor.training.eepatterns.soa.customer.service.CustomerRegistrationService;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CustomerFacadeBean implements CustomerFacade {

	@EJB
	private CustomerRegistrationService customerRegistrationService;
	
	@Override
	public void registerCustomer(Customer customer) {
		customerRegistrationService.registerCustomer(customer);
	}
}
