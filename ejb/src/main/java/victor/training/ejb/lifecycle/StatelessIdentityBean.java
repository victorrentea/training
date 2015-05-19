package victor.training.ejb.lifecycle;

import javax.ejb.Stateless;

@Stateless
public class StatelessIdentityBean implements StatelessIdentity {

	private static int COUNTER = 0;
	
	private final int myIdentity;
	
	public StatelessIdentityBean() {
		myIdentity = COUNTER ++;
	}
	
	@Override
	public int getInstanceIdentity() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return myIdentity;
	}
	
}
