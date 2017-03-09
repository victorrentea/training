package victor.training.java8.voxxed.templateMethod.deathOfTemplate;

import victor.training.java8.voxxed.templateMethod.JobParameters;
import victor.training.java8.voxxed.templateMethod.JobResults;

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
