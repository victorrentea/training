package victor.training.jee6.cdi.event.ex2;

import javax.enterprise.event.Observes;

public class BatchJob1 {
	public void runJob(@Observes Integer batchRunNumber) {
		System.out.println("Job1 runs for run#" + batchRunNumber);
	}
}
