package victor.training.jee6.cdi.event.ex2;

import javax.enterprise.event.Observes;

public class BatchJob2 {
	public void runJob(@Observes Integer batchRunNumber) {
		System.out.println("Job2 runs for run#" + batchRunNumber);
	}
}
