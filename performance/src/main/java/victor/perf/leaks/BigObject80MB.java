package victor.perf.leaks;

import java.util.Date;

public class BigObject80MB {
	public Date date = new Date();
	public int[] largeArray = new int[20 * 1024 * 1024];
}
