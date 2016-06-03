package victor.perf.leaks;

import java.util.Arrays;
import java.util.Date;

public class BigObject20MB {
	public Date date = new Date();
	public int[] largeArray = new int[5*1024*1024];
}