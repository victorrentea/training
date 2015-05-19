package victor.training.eepatterns.contextholder.thread;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class MyService {

	@EJB
	private MyDAO dao;
	
	public void doStuff(int param) {
		dao.updateImportantStuff(param);
	}
}

