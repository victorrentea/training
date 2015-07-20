package victor.training.eepatterns.httpevents;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Singleton
@Startup
public class Business {

	@Inject
	private Event<BusinessEvent> businessEvent;
	
	private static boolean active;
	
	@Resource
	private TimerService timerService;

	@PostConstruct
	public void initApp() {
		System.out.println("====== init app ======");
		timerService.createIntervalTimer(((int)(Math.random()*5 + 1)) * 1000, ((int)(Math.random()*5 + 1)) * 1000, new TimerConfig());
	}

	@Timeout
	public void tick() {
		if (active) {
			System.out.println("Business fires event");
			businessEvent.fire(new BusinessEvent("second = " + new Date().getSeconds()));
		}
	}
	
	public void activate() {
		active = true;
		System.out.println("Activated");
	}

	@PreDestroy
	public void stopTimer() {
		for (Timer timer : timerService.getTimers()) {
			timer.cancel();
		}
	}
}
