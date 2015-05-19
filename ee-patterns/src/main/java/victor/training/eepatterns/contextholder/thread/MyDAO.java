package victor.training.eepatterns.contextholder.thread;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import victor.training.eepatterns.contextholder.ContextKey;

@LocalBean
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class MyDAO {
	
	public void updateImportantStuff(int param) {
		String username = (String) ThreadLocalContextHolder.get(ContextKey.USERNAME_KEY);
		System.out.println("Username from Thread context: "+username);
		//do important stuff with that context information: e.g. CREATED_BY column
	}
}
