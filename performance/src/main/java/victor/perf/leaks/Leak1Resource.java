package victor.perf.leaks;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("leak1")
public class Leak1Resource {
	
	static ThreadLocal<BigObject20MB> threadLocal = new ThreadLocal<>();
	
	@GET
	public String test() {
		BigObject20MB bigData = new BigObject20MB();
		String s = "Just allocated: " + bigData.largeArray.length*4 + " Bytes.";
		s+="Remaining Memory: " + Runtime.getRuntime().freeMemory();
		
		threadLocal.set(bigData);
		System.out.println(s);

		return s;
	}
}
