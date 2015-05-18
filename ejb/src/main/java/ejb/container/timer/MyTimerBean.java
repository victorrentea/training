package ejb.container.timer;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

@Stateless
public class MyTimerBean implements MyTimer {
	
	// Why do you think this is WRONG ?
	public static boolean flag; 

	@Resource
	private TimerService timerService;

	@Override
	public void setTimer(Long businessId) {
		System.out.println("Setting timer after 4 seconds...");
		timerService.createTimer(4 * 1000, businessId);
	}

	@Timeout
	public void onTimeout(Timer timer) {
		Long businessId = (Long) timer.getInfo();
		System.out.println("Processing businessId: " + businessId);
		flag = true;
	}

}
