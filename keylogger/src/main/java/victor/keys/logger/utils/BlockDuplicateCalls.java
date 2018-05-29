package victor.keys.logger.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import victor.keys.logger.GlobalSettings;

public class BlockDuplicateCalls {

	private Map<Object, Long> lastCallTimes = new HashMap<>();
	
	public <T> void call(Consumer<T> f, T param) {
		if (canCall(param)) {
			f.accept(param);
		}
	}

	private <T> boolean canCall(T param) {
		long lastCallTime = lastCallTimes.getOrDefault(param, 0l);
		
		long delta = System.currentTimeMillis() - lastCallTime;
		if (delta > GlobalSettings.BLOCK_DUPLICATES_MILLIS) {
			lastCallTimes.put(param, System.currentTimeMillis());
			return true;
		}
		return false;
	}
	
}
