package victor.keys.logger.utils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class OpaqueOnHoverMouseListener implements MouseListener {
	private final JFrame frame;
	private final float transparency;

	public OpaqueOnHoverMouseListener(JFrame frame, float transparency) {
		this.frame = frame;
		this.transparency = transparency;
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
		frame.setOpacity(transparency);
	}

	public void mouseEntered(MouseEvent e) {
		frame.setOpacity(1f);
	}

	public void mouseClicked(MouseEvent e) {
	}
}