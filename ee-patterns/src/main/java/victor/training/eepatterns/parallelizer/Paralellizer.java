package victor.training.eepatterns.parallelizer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class Paralellizer {
	@Inject
	private ParalellizerWorker worker;

	public List<Integer> doALotOfWork(List<String> workItems) {
		List<Integer> results = new ArrayList<Integer>();
//		for (String workItem : workItems) { // INITIAL(
//			results.add(worker.executeWorkItem(workItem));
//		} // INITIAL)
		
		// TODO Instead of the above, launch all work items in parallel and collect Future<Integer> in a list
		// TODO For each Future, wait for it to finish (.get())
		List<Future<Integer>> asyncResults = new ArrayList<Future<Integer>>(); // SOLUTION(

		System.out.println("Parent: Starting all tasks to run in parallel...");
		for (String workItem : workItems) {
			Future<Integer> asyncResult = worker.executeWorkItem(workItem);
			asyncResults.add(asyncResult);
		}
		System.out.println("Parent: Started all tasks. Blocking to collect results...");
		for (Future<Integer> asyncResult : asyncResults) {
			try {
				results.add(asyncResult.get());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		System.out.println("Parent:Collected all results. Parallel processing done"); // SOLUTION)
		return results;
	}
}
