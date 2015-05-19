package victor.training.eepatterns;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.jms.QueueRequestor;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

import victor.training.eepatterns.payloadextractor.facade.AsyncBusinessFacadeBean;

public class AbstractArquillianTest {
	@Deployment
	public static JavaArchive getDeployment() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.jar")
				.addPackages(true, "ejb")
				.addPackages(true, "victor.training.eepatterns")
				.addPackages(true, "org.mockito")
				.addPackages(true, "org.apache.tools")
				.addPackages(true, "org.objenesis")
				.addClass(QueueRequestor.class)
				.addAsManifestResource(new File("src/main/webapp/WEB-INF/beans.xml"))
				.addAsManifestResource(new File("src/main/webapp/WEB-INF/ejb-jar.xml"));
		archive.delete(AsyncBusinessFacadeBean.class.getName().replace('.', '/') + ".class");
		return archive;
	}

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
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
