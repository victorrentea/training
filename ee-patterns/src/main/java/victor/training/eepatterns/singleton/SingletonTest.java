package victor.training.eepatterns.singleton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Singleton")
public class SingletonTest extends HttpServlet {

	@Inject
	private SMSBusiness smsBusiness;
	
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		try {
			testGatekeeper();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public void testGatekeeper() throws Throwable {
		executeInParallel(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				smsBusiness.sendSMS();
				return null;
			}
		});
		System.out.println("Test OK");
	}
	
	protected void executeInParallel(Callable<Void> callable) throws Throwable {
		executeInParallel(40, 10, callable);
	}

	protected void executeInParallel(int taskCount, int workerCount, Callable<Void> callable) throws Throwable {
		List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();
		for (int i = 0; i < taskCount; i++) {
			tasks.add(callable);
		}
		List<Future<Void>> results = Executors.newFixedThreadPool(workerCount).invokeAll(tasks);
		for (Future<Void> item : results) {
			try {
				item.get();
			} catch (ExecutionException e) {
				throw e.getCause();
			}
		}

	}

}
