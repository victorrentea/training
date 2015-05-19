package victor.training.jee6.cdi.event.ex2;

import javax.ejb.Singleton;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Singleton
public class BatchRunner {
	private @Inject
	Event<Integer> runBatchEvent;

	private int runCount;

	public void runBatchJobs() {
		System.out.println("Running batch jobs (run #" + runCount + ")...");
		runBatchEvent.fire(runCount);
		runCount++;
	}
}
