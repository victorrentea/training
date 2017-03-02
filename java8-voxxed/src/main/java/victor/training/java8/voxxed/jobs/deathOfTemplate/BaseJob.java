package victor.training.java8.voxxed.jobs.deathOfTemplate;

import victor.training.java8.voxxed.jobs.JobParameters;
import victor.training.java8.voxxed.jobs.JobResults;

public abstract class BaseJob {
	public void start(JobParameters params) {
		try {
			System.out.println("set system user on thread");
			JobResults results = doWork(params);
			storeAndLogResults(results);
		} catch (Exception e) {
			storeAndLogResults(JobResults.error(e));
		} finally {
			System.out.println("Clear thread locals...");
		}
	}

	protected abstract JobResults doWork(JobParameters params);

	private void storeAndLogResults(JobResults results) {
		System.out.println("Persist results: " + results);
	}
}
