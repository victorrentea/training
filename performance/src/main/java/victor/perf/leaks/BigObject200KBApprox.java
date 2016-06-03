package victor.perf.leaks;

import java.util.Date;
import java.util.Random;

public class BigObject200KBApprox {
	private static Random r = new Random();
	public Date date = new Date();
	public int[] largeArray = new int[(40 + r.nextInt(20))*1024];
}