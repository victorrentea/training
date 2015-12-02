package victor.mbean;

import java.lang.management.ManagementFactory;
import java.util.Scanner;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class Main {
	public static void main(String[] args) throws Exception {

		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = new ObjectName("com.example:type=Hello");
		Hello mbean = new Hello();
		mbs.registerMBean(mbean, name);

		System.out.println("Hit ENTER to exit");
		try (Scanner scanner = new Scanner(System.in)) {
			scanner.nextLine();
		}
	}
}
