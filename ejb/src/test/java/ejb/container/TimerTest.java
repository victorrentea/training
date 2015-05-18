package ejb.container;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import ejb.EJBTest;
import ejb.container.timer.MyTimer;
import ejb.container.timer.MyTimerBean;

@RunWith(Arquillian.class)
public class TimerTest extends EJBTest {

	@EJB
	private MyTimer myTimer;
	
	@Test
	public void testTimer() throws InterruptedException {
		MyTimerBean.flag = false;
		myTimer.setTimer(1L);
		assertFalse(MyTimerBean.flag);
		Thread.sleep(4 * 1000);
		assertTrue(MyTimerBean.flag);
	}

}
