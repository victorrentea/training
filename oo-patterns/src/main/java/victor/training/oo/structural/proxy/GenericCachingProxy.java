package victor.training.oo.structural.proxy;

import static java.util.Arrays.asList;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericCachingProxy {

	@SuppressWarnings("unchecked")
	public static <T> T makeProxyFor(T subject, Class<T> interfaceClass) {
		return (T) Proxy.newProxyInstance(
				GenericCachingProxy.class.getClassLoader(), 
				new Class<?>[]{interfaceClass}, // create on-demand implementation at runtime 
				new CachingHandler(subject)); // All method calls are delegated to this handler
	}
	
	// Note: Silly cache implementation. Such code might produce OutOfMemoryErrors.
	private static class CachingHandler implements InvocationHandler {
		private final Object delegate;
		private Map<List<?>, Object> cache = new HashMap<>(); 
		
		public CachingHandler(Object delegate) {
			this.delegate = delegate;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			List<?> cacheKey = getCacheKey(method.getName(), args);
			if (cache.containsKey(cacheKey)) {
				return cache.get(cacheKey);
			} else {
				Object result = method.invoke(delegate, args);
				cache.put(cacheKey, result);
				return result;
			}
		}
		
		private List<Object> getCacheKey(String methodName, Object... args) {
			List<Object> list = new ArrayList<>();
			list.add(methodName);
			list.addAll(asList(args));
			return list;
		}
	}
}
