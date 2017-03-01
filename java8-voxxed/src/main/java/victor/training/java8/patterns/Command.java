package victor.training.java8.patterns;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Command {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Runnable simpleCommand = () -> {System.out.println("Execute simple command");};
		Runnable complexCommand = createComplexCommand();
		
		executor.submit(simpleCommand);
		executor.submit(complexCommand);
		
		Thread.sleep(1000);
		stop(executor);
	}

	private static Runnable createComplexCommand() {
		String closureParameter = "a";
		// other required instance-field dependencies 
		return () -> {
			System.out.println("Execute complex command with parameter " + closureParameter);
		};
	}

	private static void stop(ExecutorService executor) {
		try {
		    System.out.println("attempt to shutdown executor");
		    executor.shutdown();
		    executor.awaitTermination(5, TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
		    System.err.println("tasks interrupted");
		}
		finally {
		    if (!executor.isTerminated()) {
		        System.err.println("cancel non-finished tasks");
		    }
		    executor.shutdownNow();
		    System.out.println("shutdown finished");
		}
	}
}
