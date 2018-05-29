package victor.keys.logger.utils;

import javax.swing.JFrame;

public class Fadeout {
	private final long tstart;
	private final long tend;
	private final JFrame frame;
	private final float initialOpacity;

	public Fadeout(int durationMillis, JFrame frame) {
		this.frame = frame;
		initialOpacity = frame.getOpacity();
		tstart = System.currentTimeMillis();
		tend = tstart + durationMillis;
	}

	public void tick() {
		float opacity = initialOpacity * Math.max(0, (tend - System.currentTimeMillis()) * 1f / (tend - tstart));
		frame.setOpacity(opacity);
	}

	public boolean hasDissapeared() {
		return System.currentTimeMillis() > tend;
	}
}