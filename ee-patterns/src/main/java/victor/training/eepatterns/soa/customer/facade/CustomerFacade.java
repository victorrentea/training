package victor.training.eepatterns.soa.customer.facade;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import victor.training.eepatterns.soa.customer.Customer;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class CustomerFacade implements Customer {

}
