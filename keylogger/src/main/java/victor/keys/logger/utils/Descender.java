package victor.keys.logger.utils;

import javax.swing.JFrame;

public class Descender {
	private final JFrame frame;
	private long yend;
	private static final float SPEED_RATIO = 0.3f;// 200 px per sec

	public Descender(JFrame frame) {
		this.frame = frame;
		yend = frame.getY();
	}

	public void descend(int totalDeltaY) {
		yend += totalDeltaY;
	}

	public void tick() {
		if (frame.getY() < yend) {
			int deltaY = (int) Math.max(1, (int) ((yend - frame.getLocation().y) * SPEED_RATIO));
			frame.setLocation(frame.getLocation().x, frame.getLocation().y + deltaY);
		}
	}

}