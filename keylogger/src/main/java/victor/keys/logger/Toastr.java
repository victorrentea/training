package victor.keys.logger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.commons.lang.StringUtils;

import victor.keys.logger.utils.Descender;
import victor.keys.logger.utils.Fadeout;
import victor.keys.logger.utils.HorizontalPositioner;

@SuppressWarnings("serial")
public class Toastr extends JFrame {
	private static final int HEIGHT = 70;
	private static final int Y_GAP = 20;
	private static final int FADEOUT_DURATION = 800;
	private final long startFadeTime;
	private Fadeout fadeout;
	private Descender descender;
	private HorizontalPositioner horizontalPositioner = new HorizontalPositioner(this, GlobalSettings.PIXELS_TO_RIGHT);

	public static Toastr makeShortcut(Shortcut shortcut, int duration) {
		Toastr toastr = new Toastr(duration);
		toastr.buildContent(shortcut);
		return toastr;
	}
	
	public static Toastr makeWebinarNotification(int duration) {
		Toastr toastr = new Toastr(duration);
		toastr.getContentPane().setLayout(new GridLayout(2,1));
		JLabel url = new JLabel("Webinar Welcome Page", SwingConstants.CENTER);
		url.setForeground(Color.lightGray);
		url.setFont(new Font("Montserrat Bold", 0, 18));
		toastr.getContentPane().add(url);
		JLabel url2 = new JLabel("http://victorrentea.ro/#webinar", SwingConstants.CENTER);
		url2.setForeground(Color.gray);
		url2.setFont(new Font("Montserrat Bold", 0, 15));
		toastr.getContentPane().add(url2);
		return toastr;
	}

	private Toastr(int duration) {
		startFadeTime = System.currentTimeMillis() + duration - FADEOUT_DURATION;
		setUndecorated(true);
//		getContentPane().setBackground(new Color(81, 163, 81));
		getContentPane().setBackground(GlobalSettings.TOASTR_BG_COLOR);
		setShape(new RoundRectangle2D.Double(0, 0, GlobalSettings.WIDTH, HEIGHT, 20, 20));
		setSize(GlobalSettings.WIDTH, HEIGHT);
		
		horizontalPositioner.tick();
		setLocation(getX(), -HEIGHT + 20);
		descender = new Descender(this);
		setAlwaysOnTop(true);
		setFocusable(false);
		setType(Type.UTILITY);
		setFocusableWindowState(false);
		setOpacity(GlobalSettings.TOASTR_BASE_OPACITY);
		setVisible(true);
	}
	
	public void paint(Graphics g) {
		super.paint(g); 
		 Graphics2D g2d = (Graphics2D) g;

         Color oldColor = g2d.getColor();
         g2d.setColor(GlobalSettings.TOASTR_FONT_COLOR);

         int x = 0, y = 0, width = getWidth(), height = getHeight();
         int radius = 20;
         int thickness = 2;
         RoundRectangle2D.Float outer = new RoundRectangle2D.Float(x, y, width, height, radius, radius);
         RoundRectangle2D.Float inner = new RoundRectangle2D.Float(x + thickness, y + thickness, width - thickness * 2, height - thickness * 2, radius, radius);

         Path2D path = new Path2D.Float(Path2D.WIND_EVEN_ODD);
         path.append(outer, false);
         path.append(inner, false);
         g2d.fill(path);
         g2d.setColor(oldColor);
	}

	private void buildContent(Shortcut shortcut) {
		getContentPane().setLayout(new BorderLayout());
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(null);
//		mainPanel.setBorder(new MyLineBorder(Color.black, 1, 10));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout());
		
		String correctedTitle = shortcut.getTitle().replace("UP", "↑").replace("DOWN", "↓");
		JLabel title = new JLabel(correctedTitle, SwingConstants.CENTER);
		
		title.setForeground(GlobalSettings.TOASTR_FONT_COLOR);
		title.setFont(new Font("Montserrat Bold", 0, 20));
		mainPanel.add(title, BorderLayout.CENTER);

		int cols = 1;
		if (GlobalSettings.showEclipse && GlobalSettings.showIntelliJ) {
			cols = 2;
			JLabel eclipseIcon;
			if (StringUtils.isNotBlank(shortcut.getEclipseWin())) {
				eclipseIcon = new JLabel(createImageIcon("/eclipse18.png", "eclipse"));
			}
			else {
				eclipseIcon = new JLabel("");
				eclipseIcon.setMinimumSize(new Dimension(18,18));
			}
			mainPanel.add(eclipseIcon, BorderLayout.LINE_START);
			JLabel ideaIcon;
			if (StringUtils.isNotBlank(shortcut.getIdeaWin())) {
				ideaIcon = new JLabel(createImageIcon("/intellij18.png", "intelliJ"));
			} else {
				ideaIcon = new JLabel("          ");
			}
			mainPanel.add(ideaIcon, BorderLayout.LINE_END);
		}

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, cols, 0 , 20));
		if (GlobalSettings.showMac) {
			if (GlobalSettings.showEclipse)
				topPanel.add(ShortcutLabelMaker.makeMacShortcut(shortcut.getEclipseMac()));
			if (GlobalSettings.showIntelliJ)
				topPanel.add(ShortcutLabelMaker.makeMacShortcut(shortcut.getIdeaMac()));
		}
		if (topPanel.getComponentCount() == 0) {
			topPanel.add(new JLabel(" "));
		}
		topPanel.setBackground(null);
		mainPanel.add(topPanel, BorderLayout.NORTH);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, cols, 0 , 20));
		if (GlobalSettings.showWin) {
			if (GlobalSettings.showEclipse)
				bottomPanel.add(ShortcutLabelMaker.makeShortcut(shortcut.getEclipseWin()));
			if (GlobalSettings.showIntelliJ)
				bottomPanel.add(ShortcutLabelMaker.makeShortcut(shortcut.getIdeaWin()));
		} else {
			bottomPanel.add(new JLabel(" "));
		}
		bottomPanel.setBackground(null);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
	}

	

	

	public ImageIcon createImageIcon(String path, String description) {
		return new ImageIcon(getClass().getResource(path), description);
	}

	

	public void tick() {
		if (fadeout == null) {
			if (System.currentTimeMillis() > startFadeTime && fadeout == null) {
				fadeout = new Fadeout(FADEOUT_DURATION, this);
			}
		} else {
			fadeout.tick();
		}
		descender.tick();
		horizontalPositioner.tick();
	}

	public void descend() {
		descender.descend(HEIGHT + Y_GAP);
	}

	public boolean hasDissapeared() {
		return fadeout != null && fadeout.hasDissapeared();
	}
}
