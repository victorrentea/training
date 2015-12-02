package victor.mxbean;

import java.lang.management.ManagementFactory;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class Main {

	public static void main(String[] args) throws Exception {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		ObjectName mxbeanName = new ObjectName("com.example:type=QueueSampler");

		Queue<String> queue = new ArrayBlockingQueue<String>(10);
		queue.add("Request-1");
		queue.add("Request-2");
		queue.add("Request-3");
		QueueSampler mxbean = new QueueSampler(queue);

		mbs.registerMBean(mxbean, mxbeanName); // MIND the instance passed here ! It must be one / JVM !!		
		
		System.out.println("Hit ENTER to exit");
		try (Scanner scanner = new Scanner(System.in)) {
			scanner.nextLine();
		}
	}
}
