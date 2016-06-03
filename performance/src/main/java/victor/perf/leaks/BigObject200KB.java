package victor.perf.leaks;

import java.util.Date;

public class BigObject200KB {
	public Date date = new Date();
	public int[] largeArray = new int[50*1024];
}