package ejb.lifecycle;

import javax.ejb.Stateless;

@Stateless
public class PoolSizeBean implements PoolSize {

	private static int counter = 0;

	public PoolSizeBean() {
		counter++;
	}

	@Override
	public int getInstanceCount() {
		return counter;
	}

	@Override
	public void method(int sleepMillis) {
		if (sleepMillis > 0) {
			try {
				Thread.sleep(sleepMillis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
