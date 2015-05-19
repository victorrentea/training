package victor.training.jee6.ejb.singleton;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class SynchronizedSingleton {

	public synchronized void synchronizedMethod(int threadId) {
		System.out.println("Entering guarded method");
		try {
			Thread.sleep(3 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Exiting guarded method");
	}
}
