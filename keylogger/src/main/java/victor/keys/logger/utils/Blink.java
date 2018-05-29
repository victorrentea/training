package victor.keys.logger.utils;

import java.util.function.Consumer;

public class Blink {
	private final int interval;
	private final Consumer<Boolean> action;
	private boolean state;
	private Long lastTime;
	public Blink(int interval, Consumer<Boolean> action) {
		this.interval = interval;
		this.action = action;
	}

	public void tick() {
		long now = System.currentTimeMillis();
		if (lastTime == null || now - lastTime > interval) {
			lastTime = now;
			state = !state;
			action.accept(state);
		}
	}
	
}
