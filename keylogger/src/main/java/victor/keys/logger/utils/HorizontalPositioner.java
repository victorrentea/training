package victor.keys.logger.utils;

import java.awt.Toolkit;

import javax.swing.JFrame;

import victor.keys.logger.GlobalSettings;

public class HorizontalPositioner{
	
	private int lastScreenWidth = 0;
	private final JFrame frame;
	private int spaceToRight;
	
	public HorizontalPositioner(JFrame frame, int spaceToRight) {
		this.frame = frame;
		this.spaceToRight = spaceToRight;
	}

	public void tick() {
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getSize().width;
		if (screenWidth != lastScreenWidth) {
			lastScreenWidth = screenWidth;
			frame.setLocation(lastScreenWidth - frame.getWidth() - spaceToRight, frame.getY());
		}
	}
}