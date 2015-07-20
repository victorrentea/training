package victor.training.eepatterns.parallelizer;

import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

@Stateless
public class ParalellizerWorker {

	private static int NEXT_ID = 1;
	private final int workerId = NEXT_ID++;

	// TODO mark method as async, and change it to return Future<> instead
	@Asynchronous // SOLUTION
	//public Integer executeWorkItem(String workItem) { // INITIAL
	public Future<Integer> executeWorkItem(String workItem) { // SOLUTION
		int result = workItem.length();
		System.out.println("Worker " + workerId + ": Start processing item '" + workItem + "'");
		
		System.out.println("Worker " + workerId + ": " + Thread.currentThread().getName());
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Worker " + workerId + ": Item '" + workItem + "' done.");
		
		return new AsyncResult<Integer>(result); // SOLUTION
//		return result; // INITIAL
	}
}
