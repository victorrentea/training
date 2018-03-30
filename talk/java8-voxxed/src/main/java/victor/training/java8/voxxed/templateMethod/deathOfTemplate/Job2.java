package victor.training.java8.voxxed.templateMethod.deathOfTemplate;

import victor.training.java8.voxxed.more.fake.Service;
import victor.training.java8.voxxed.templateMethod.JobParameters;
import victor.training.java8.voxxed.templateMethod.JobResults;

@Service
public class Job2 extends BaseJob {

	@Override
	protected JobResults doWork(JobParameters params) {
		System.out.println("Do Job2 with params");
		return JobResults.success();
	}

}
