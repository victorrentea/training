package victor.training.ejb;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.ejb.EJB;
import javax.jms.QueueRequestor;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import victor.training.ejb.container.tx.testutil.TransactionUtil;

public class EJBTest {
	@Deployment
	public static JavaArchive getDeployment() {
		return ShrinkWrap.create(JavaArchive.class, "test.jar").addPackages(true, "victor.training.ejb")
				.addPackages(true, "org.mockito")
				.addPackages(true, "org.objenesis").addClass(QueueRequestor.class);
	}

	@EJB
	protected TransactionUtil txUtil;
	
	protected void executeInParallel(Callable<Void> callable) throws Throwable {
		executeInParallel(40,10,callable);
	}
	protected void executeInParallel(int taskCount, int workerCount, Callable<Void> callable) throws Throwable{
		List<Callable<Void>> tasks = new ArrayList<>();
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
	
	protected Answer executesInValidTransaction() {
		return executesInValidTransaction("");
	}

	@SuppressWarnings("rawtypes")
	protected Answer executesInValidTransaction(final String prefix) {
		return new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				assertTrue(prefix + ": Executes in VALID Transaction", txUtil.isTransactionValid());
				return null;
			}
	
		};
	}
	@SuppressWarnings("rawtypes")
	protected Answer executesWithoutTransaction() {
		return new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				assertTrue("Executes without Transaction", !txUtil.hasTransaction());
				return null;
			}
	
		};
	}
}
