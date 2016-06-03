package victor.perf.stock.loadtest;

import static java.util.stream.Collectors.toList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.apache.commons.io.IOUtils;

public class PerformanceTest {
	
	private static final int TEST_WINDOW_SEC = 6;
	private static final int N_THREADS = 16;
	static final String URI = "http://localhost:8089/performance/stock?cacheMB=100";
	
	public static void main(String[] args) throws IOException, Exception {
		List<List<Integer>> statsHistory = new ArrayList<>();
		List<Integer> lastResults;
		
		System.out.println("[AVG, P90%, P99%]");
		while (true) {
			lastResults = executeParallelLoadTest();
			List<Integer> stats = getStats(lastResults);
			System.out.println(stats + " ("+lastResults.size()+" requests)");
			
			if (statsHistory.size() >= 2) {
				List<Integer> lastStats1 = statsHistory.get(statsHistory.size()-1);
				List<Integer> lastStats2 = statsHistory.get(statsHistory.size()-2);
				if (similar(stats, lastStats1) && 
					similar(lastStats2, lastStats1) && 
					similar(stats, lastStats2)) {
					System.out.println("Found 3 similar consecutive stats. STOP");
					break;
				}
			}
			statsHistory.add(stats);
		}
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.csv"))) {
			IOUtils.writeLines(lastResults, "\n", writer);
			System.out.println("File written");
		}
		System.out.println("Shuting down executor");
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.SECONDS);
		System.out.println("Program terminated");		
	}

	private static boolean similar(List<Integer> statsA, List<Integer> statsB) {
		for (int i=0;i<statsA.size();i++) {
			if (Math.abs(statsA.get(i) - statsB.get(i)) > 1) {
				return false;
			}
		}
		return true;
	}

	static ExecutorService executor = Executors.newCachedThreadPool();

	private static List<Integer> executeParallelLoadTest() throws Exception {
		List<Worker> tasks = IntStream.range(0, N_THREADS)
				.mapToObj(i->new Worker(URI, TEST_WINDOW_SEC * 1000))
				.collect(toList());
		
		System.out.print("Starting " + tasks.size() + " workers for "+TEST_WINDOW_SEC + " seconds. ");
		final long phaseEndTime = System.currentTimeMillis() + TEST_WINDOW_SEC * 1000;
		new Timer().schedule(new TimerTask() {
			public void run() {
				long millisLeft = phaseEndTime - System.currentTimeMillis();
				long secondsLeft = Math.round(millisLeft/1000.0);
				System.out.print(" " + secondsLeft);
				if (secondsLeft == 0) {
					cancel();
					System.out.print(" DONE: ");
				}				
			}
		}, 0, 1000);
		
		Thread.sleep(100);
		List<Future<List<Integer>>> futures = executor.invokeAll(tasks);
		
		List<Integer> allResults = new ArrayList<>();
		for (Future<List<Integer>> future : futures) {
			allResults.addAll(future.get());
		}
		return allResults;
	}

	
	public static int average(List<Integer> results) {
		return (int) results.stream()
				.mapToInt(i->i)
				.average()
				.getAsDouble();
	}
	
	public static int percentile(List<Integer> results, int percent) {
		return (int) results.stream()
				.mapToInt(i->i)
				.sorted()
				.skip(percent*results.size() / 100)
				.findFirst()
				.getAsInt();
	}
	
	public static List<Integer> getStats(List<Integer> results) {
		return Arrays.asList(average(results), percentile(results, 90), percentile(results, 99));
	}
}
