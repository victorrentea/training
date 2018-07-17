package victor.training.java8.voxxed.templateMethod.birthOfLambdas;

import victor.training.java8.voxxed.more.fake.Service;
import victor.training.java8.voxxed.templateMethod.JobParameters;
import victor.training.java8.voxxed.templateMethod.JobResults;

@Service
public class SimplerJob2 {

	public JobResults doWork(JobParameters params) {
		System.out.println("Do Job2 with params");
		return JobResults.success();
	}

}
