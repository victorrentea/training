package victor.training.jee7.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;

@Stateless
public class MyExecutor {
	@Resource
	private ManagedExecutorService executorService;

	@Resource
	private ManagedScheduledExecutorService schedulerService;

	public List<Future<String>> executeAllInParallel(List<? extends Callable<String>> tasks) throws InterruptedException {
		List<Future<String>> results = new ArrayList<>();
		for (Callable<String> task : tasks) {
			results.add(executorService.submit(task));
		}
		return results;
	}

	public ScheduledFuture<String> scheduleExecutionAfter3Seconds(Callable<String> task) {
		return schedulerService.schedule(task, 3, TimeUnit.SECONDS);
	}
}
