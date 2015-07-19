package victor.training.eepatterns.contextholder.thread;

import java.util.HashMap;
import java.util.Map;

import victor.training.eepatterns.contextholder.ContextKey;

public class ThreadLocalContextHolder {
	
	// TODO define a ThreadLocal<Map<ContextKey, Object>>
	private static ThreadLocal<Map<ContextKey, Object>> THREAD_CONTEXT = new ThreadLocal<Map<ContextKey, Object>>(); // SOLUTION

	private static Map<ContextKey, Object> getMap() {
		// TODO .get and .set this map from Thread local;
		// return new HashMap<ContextKey, Object>(); // INITIAL 
		if (THREAD_CONTEXT.get() == null) { // SOLUTION(
			THREAD_CONTEXT.set(new HashMap<ContextKey, Object>());
		}
		return THREAD_CONTEXT.get(); // SOLUTION)
	}

	public static void put(ContextKey key, Object value) {
		getMap().put(key, value);
	}
	
	public static Object get(ContextKey key) {
		return getMap().get(key);
	}
	
	public static void cleanupThread() {
		// TODO cleanup !!
		THREAD_CONTEXT.remove(); // SOLUTION
	}

}
