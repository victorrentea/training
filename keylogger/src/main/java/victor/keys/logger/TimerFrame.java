package victor.keys.logger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.StringUtils;

import victor.keys.logger.utils.Blink;
import victor.keys.logger.utils.HorizontalPositioner;

@SuppressWarnings("serial")
public class TimerFrame extends JFrame {
	private HorizontalPositioner horizontalPositioner = new HorizontalPositioner(this, 
			GlobalSettings.PIXELS_TO_RIGHT + GlobalSettings.WIDTH + 20);
	
	private JProgressBar progressBar;
	private Blink blink;

	public TimerFrame() {
		setUndecorated(true);
		getContentPane().setBackground(Color.lightGray);
		setShape(new RoundRectangle2D.Double(0, -20, GlobalSettings.WIDTH/2, 44, 20, 20));
		setSize(GlobalSettings.WIDTH/2, 24);
		setLocation(getX(), 0);
		horizontalPositioner.tick();
		setAlwaysOnTop(true);
		setFocusable(false);
		setType(Type.UTILITY);
		setFocusableWindowState(false);

		getContentPane().setLayout(new BorderLayout());
		
		progressBar = new JProgressBar();
		progressBar.setForeground(Color.lightGray);
		progressBar.setBackground(Color.GREEN);
		progressBar.setString("1 min");
		progressBar.setStringPainted(true);
		progressBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		progressBar.setBorderPainted(false);
		progressBar.setValue(50);
		progressBar.setFont(new Font("Consolas", Font.BOLD, 19));
		progressBar.addMouseListener(new ProgressMouseListener());
		progressBar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		getContentPane().add(progressBar);
	}
	
	public void display() {
		state = State.STOPPED;
		timeLeft= totalTime;
		refreshBar();
		setVisible(true);
	}
	
	State state = State.STOPPED;
	long totalTime = GlobalSettings.DEFAULT_TIMER_MILLIS;
	long timeLeft = totalTime;
	
	enum State {
		STOPPED, RUNNING, PAUSED, OVERDUE
	}

	private void doubleClick() {
		state = State.STOPPED;
		timeLeft = totalTime;
		refreshBar();
		blink = null;
		progressBar.setStringPainted(true);
	}
	
	private void oneClick() {
		switch (state) {
		case STOPPED:
		case OVERDUE:
			state = State.RUNNING;
			timeLeft = totalTime;
			break;
		case RUNNING:
			state = State.PAUSED;
			blink = new Blink(700, b -> progressBar.setStringPainted(!b));
			break;
		case PAUSED:
			state = State.RUNNING;
			blink = null;
			progressBar.setStringPainted(true);
			break;
		}
	}
	
	private Long lastTick;
	public void tick() {
		long now = System.currentTimeMillis();
		horizontalPositioner.tick();
		if (blink != null) {
			blink.tick();
		}
		if (lastTick == null) {
			lastTick = now;
		} else {
			long delta = now - lastTick;
			if (state == State.RUNNING) {
//				delta *= 30; // Uncomment this to test with faster time :)
				timeLeft -= delta;
				
				refreshBar();
			}
			lastTick = now;
		}
	}

	private void refreshBar() {
		int ratio = (int) (100 - 100 * timeLeft/totalTime);
		progressBar.setValue(ratio);
		if (timeLeft < 0) {
			state = State.OVERDUE;
			progressBar.setValue(100);
			blink = new Blink(1000, b -> progressBar.setForeground(b?Color.red:Color.lightGray));
		}
		progressBar.setString(getLabel());
	}

	private String getLabel() {
		if (state == State.OVERDUE) {
			return "Time's UP!";
		} else {
			if (timeLeft > 30_000) {
				return ((int)Math.round(timeLeft / 60f / 1000)) + " min";
			} else {
				long seconds = timeLeft/1000;
				return "0:" + StringUtils.leftPad(""+ seconds, 2, '0');
			}
		}
	}
	
	private void setMinutes(int minutes) {
		int newTotalTime = minutes * 60 * 1000;
		long delta = newTotalTime - totalTime;
		
		totalTime = newTotalTime;
		timeLeft += delta;
		refreshBar();
	}
	
	private JPopupMenu makePopupMenu() {
		JPopupMenu popup = new JPopupMenu();
		for (int i=2; i< 20;i++) {
			final int minutes = i;
			JRadioButtonMenuItem  menuItem = new JRadioButtonMenuItem(minutes + " min");
			if (minutes == totalTime / 60 / 1000 ) {
				menuItem.setSelected(true);
			}
			menuItem.addActionListener(e -> setMinutes(minutes));
			popup.add(menuItem);
		}
		return popup;
	}
	
	class ProgressMouseListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			if (SwingUtilities.isRightMouseButton(e) ) {
				//right click
				return;
			} else if (e.getClickCount() == 1) {
				oneClick();
			} else if (e.getClickCount() == 2) {
				doubleClick();
			}
		}
		
		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}
		
		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}
		private void maybeShowPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				JPopupMenu popup = makePopupMenu();
				
				popup.show(e.getComponent(),
						e.getX(), e.getY());
			}
		}
		
		public void mouseEntered(MouseEvent e) {
		}
		
		public void mouseExited(MouseEvent e) {
		}
		
	}


}
