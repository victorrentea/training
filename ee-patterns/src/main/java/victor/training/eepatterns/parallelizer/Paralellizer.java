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
		List<Future<Integer>> asyncResults = new ArrayList<Future<Integer>>();

		System.out.println("Parent: Starting all tasks to run in parallel...");
		for (String workItem : workItems) {
			Future<Integer> asyncResult = worker.executeWorkItem(workItem);
			asyncResults.add(asyncResult);
		}
		System.out.println("Parent: Started all tasks. Blocking to collect results...");
		List<Integer> results = new ArrayList<Integer>();
		for (Future<Integer> asyncResult : asyncResults) {
			try {
				results.add(asyncResult.get());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		System.out.println("Parent:Collected all results. Parallel processing done");
		return results;
	}
}
