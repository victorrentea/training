package victor.training.eepatterns.contextholder.thread;

import java.util.HashMap;
import java.util.Map;

import victor.training.eepatterns.contextholder.ContextKey;

public class ThreadLocalContextHolder {
	private static ThreadLocal<Map<ContextKey, Object>> THREAD_CONTEXT = new ThreadLocal<Map<ContextKey, Object>>();

	private static Map<ContextKey, Object> getMap() {
		if (THREAD_CONTEXT.get() == null) {
			THREAD_CONTEXT.set(new HashMap<ContextKey, Object>());
		}
		return THREAD_CONTEXT.get();
	}

	public static void put(ContextKey key, Object value) {
		getMap().put(key, value);
	}
	
	public static Object get(ContextKey key) {
		return getMap().get(key);
	}
	
	public static void cleanupThread() {
		THREAD_CONTEXT.remove();
	}

}
