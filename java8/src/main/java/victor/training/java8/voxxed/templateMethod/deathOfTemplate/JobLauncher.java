package victor.training.java8.voxxed.templateMethod.deathOfTemplate;

import victor.training.java8.voxxed.more.fake.Autowired;
import victor.training.java8.voxxed.more.fake.Service;
import victor.training.java8.voxxed.templateMethod.JobParameters;

@Service
public class JobLauncher {

	@Autowired
	private Job1 job1;
	
	@Autowired
	private Job2 job2;
	
	public void start(String jobName, JobParameters params) {
		switch (jobName) {
		case "job1": job1.start(params); break;
		case "job2": job2.start(params); break;
		}
	}
}
