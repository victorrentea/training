package victor.training.jee7.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@Startup
public class MyExecutorClient {
	@Inject
	private MyExecutor myExecutor;
	
	private static final Logger log = LoggerFactory.getLogger(MyExecutorClient.class);

	public String testAll() throws InterruptedException, ExecutionException {
		testScheduler();
		return testExecutor();
	}

	private void testScheduler() {
		myExecutor.scheduleExecutionAfter3Seconds(new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(500);
				log.error("EXECUTE NOW");
				return UUID.randomUUID().toString();
			}
		});

	}

	private String testExecutor() throws InterruptedException, ExecutionException {
		List<Callable<String>> tasks = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			tasks.add(new Callable<String>() {
				@Override
				public String call() throws Exception {
					Thread.sleep(500);
					return UUID.randomUUID().toString();
				}
			});
		}
		for (Future<String> future : myExecutor.executeAllInParallel(tasks)) {
			future.get();
		}
		return "all tasks completed";
	}
}
