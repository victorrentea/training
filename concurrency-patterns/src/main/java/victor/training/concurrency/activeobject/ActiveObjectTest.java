package victor.training.concurrency.activeobject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ActiveObjectTest {
	private static final Logger log = LoggerFactory.getLogger(ActiveObjectTest.class);
	public ActiveObject activeObject;
	
	@Before
	public void startActiveObject() {
		activeObject = new ActiveObject();
	}
	
	@After
	public void stopActiveObject() {
		activeObject.stopActiveObject();
	}
	
	@Rule
	public MethodRule watchman = new TestWatchman() {
	   public void starting(FrameworkMethod method) {
	      log.debug("-----------------------------------\n\n-- Starting test: "+ActiveObjectTest.class.getSimpleName()+"." + method.getName() + " --");
	   }
	};
	

	@Test
	public void show1Call() throws Exception {
		Future<Integer> result = activeObject.delayedSum(1, 1);
		System.out.println("Sent call");
		assertEquals(2, (int) result.get());
		System.out.println("Done");
	}
	
	@Test
	public void showDelayedException() throws Exception {
		Future<Void> result = activeObject.delayedException();
		try {
			result.get();
		} catch (ExecutionException e) {
			assertTrue(e.getCause() instanceof IOException);
			return;
		}
		fail("Expected ExecutionException");
	}
	
	@Test(expected = TimeoutException.class)
	public void showInsufficientWaitingTime() throws Exception {
		log.debug("Jello");
		Future<Integer> result = activeObject.delayedSum(1, 1);
		result.get(1, TimeUnit.MILLISECONDS);
	}
	
	@Test
	public void showMethodGuards() throws InterruptedException {
		activeObject.suspendWrites();
		
		Future<String> write = activeObject.write();
		Thread.sleep(100);
		assertFalse(write.isDone());
		
		activeObject.resumeWrites();
		
		Thread.sleep(100);
		assertTrue(write.isDone());
		
	}

}
