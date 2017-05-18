package victor.training.spring.web.jmx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Profile("web")
@Component
@ManagedResource(objectName="bean:name=batchLauncher")
public class BatchLauncher {
	//add more metadata as in http://stackoverflow.com/questions/8137983/spring-mbeanexporter-giving-name-to-mbean
	// or http://docs.spring.io/spring/docs/current/spring-framework-reference/html/jmx.html

	@Autowired
	private TaskExecutor executor;
	
	@Autowired
	private Job1 job1;
	
	@ManagedOperation
	public void startJob(String jobName) {
		switch (jobName.toUpperCase()) {
		case "JOB1": startJob1(); return;
		default :throw new IllegalArgumentException("Unknown job name: " + jobName);
		}
	}
	
//	@Scheduled(cron = "*/5 * * * * *")
	public void startJob1() {
		executor.execute(job1::doStuff);
	}
	
	
	
}
