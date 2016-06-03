package victor.perf.leaks;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.enterprise.context.SessionScoped;

@SuppressWarnings("serial")
@SessionScoped
public class SessionContext implements Serializable {

	private Map<String, BigObject20MB> cache = new HashMap<>();
	
	public BigObject20MB tryCache(String key, Callable<BigObject20MB> loadMethod) throws Exception {
		if (cache.containsKey(key)) {
			return cache.get(key);
		}
		BigObject20MB newObject = loadMethod.call();
		cache.put(key, newObject);
		return newObject;
	}
}
