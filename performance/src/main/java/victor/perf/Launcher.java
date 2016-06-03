package victor.perf;

import static java.util.stream.Collectors.joining;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import victor.perf.stock.PrimeGenerator;

public class Launcher {

	private static int N = 10;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		String classpathJars = Arrays.stream(new File("target/lib").listFiles())
				.map(File::getName)
				.map(name -> "target/lib/"+name)
				.collect(joining(File.pathSeparator));
		
//		String command = StockPriceHistoryBatcher.class.getCanonicalName() + " ";
//		String command = " -XX:+UseConcMarkSweepGC -XX:+UseParNewGC " + StockPriceHistoryBatcher.class.getCanonicalName() ;
		String command =  " -XX:+UseConcMarkSweepGC -XX:+UseParNewGC " +PrimeGenerator.class.getCanonicalName() ;
		
		String fullCommand = "java -cp target/classes"+File.pathSeparatorChar+classpathJars+" " + command;
		System.out.println(fullCommand);
		
		
		List<Long> results = new ArrayList<>();
		for (int i = 0; i<N; i++) {
			long time = execute(fullCommand);
			results.add(time);
			System.out.println(generateReport(results));
		}
	}

	private static String generateReport(List<Long> results) {
		double average = results.stream().mapToLong(i->i).average().getAsDouble();
		double stdev = Math.sqrt(results.stream()
				.map(v -> v - average)
				.mapToDouble(v -> v*v)
				.sum() / (results.size()-1));
		return (int)average + " [" + (int)(average-stdev) + " - " + (int)(average+stdev)+"], n="+results.size() + ", stdev="+(int)stdev;
	}

	private static long execute(String fullCommand) throws IOException, InterruptedException {
		long t0 = System.currentTimeMillis();
		ProcessBuilder pb = new ProcessBuilder((fullCommand ).split("\\s+"));
		pb.inheritIO();
		Process p = pb.start();
		p.waitFor();
		return System.currentTimeMillis()-t0;
	}
}
