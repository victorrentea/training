package victor.perf.stock.loadtest;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class Worker implements Callable<List<Integer>> {
	private final HttpClient client = new HttpClient();
	private final long millisToRun;
	private final String uri;
	
	private long t00;

	public Worker(String uri, long millisToRun) {
		this.uri = uri;
		this.millisToRun = millisToRun;
	}

	@Override
	public List<Integer> call() throws Exception {
		int[] array = new int[1_000_000];
		int n=0;
		t00 = System.currentTimeMillis();
		while (true) {
			if (System.currentTimeMillis() - t00 > millisToRun) {
				break;
			}
			GetMethod get = new GetMethod(uri);

			long t0 = System.currentTimeMillis();	
			int errorCode = client.executeMethod(get);
			int dt = (int) (System.currentTimeMillis() - t0);
			
			get.releaseConnection();
			if (errorCode != 200) {
				System.err.println("Returned error code "+errorCode);
			}
			array[n++]=dt;
		}
		
		return Arrays.stream(array).boxed().limit(n).collect(toList());
	}
}