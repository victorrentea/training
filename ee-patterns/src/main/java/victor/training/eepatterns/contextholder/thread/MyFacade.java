package victor.training.eepatterns.contextholder.thread;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class MyFacade {

	@Inject
	private MyService service;

	public void businessMethod() {
		service.doStuff(1);
	}
}
