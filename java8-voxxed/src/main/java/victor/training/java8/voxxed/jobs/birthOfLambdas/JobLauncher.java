package victor.training.java8.voxxed.jobs.birthOfLambdas;

import victor.training.java8.fake.Autowired;
import victor.training.java8.voxxed.jobs.JobParameters;

public class JobLauncher {

	@Autowired
	private SimplerJob1 job1;
	
	@Autowired
	private SimplerJob2 job2;
	
	@Autowired
	private JobService jobService;
	
	public void start(String jobName, JobParameters params) {
		switch (jobName) {
		case "job1": jobService.start(() -> job1.doWork(params)); break;
		case "job2": jobService.start(() -> job2.doWork(params)); break;
		}
	}
}
