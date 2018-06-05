package victor.keys.logger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.net.URI;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import lombok.SneakyThrows;
import victor.keys.logger.utils.DetermineActiveIDE.IDE;
import victor.keys.logger.utils.HorizontalPositioner;
import victor.keys.logger.utils.OpaqueOnHoverMouseListener;

@SuppressWarnings("serial")
public class ControlsFrame extends JFrame {
	private HorizontalPositioner horizontalPositioner = new HorizontalPositioner(this,GlobalSettings.PIXELS_TO_RIGHT);

	public ControlsFrame() {
		setUndecorated(true);
		getContentPane().setBackground(Color.lightGray);
		setShape(new RoundRectangle2D.Double(0, -20, GlobalSettings.WIDTH, 44, 20, 20));
		setSize(GlobalSettings.WIDTH, 24);
		setLocation(getX(), 0);
		horizontalPositioner.tick();
		setAlwaysOnTop(true);
		setFocusable(false);
		setType(Type.UTILITY);
		setFocusableWindowState(false);
		
		getContentPane().setLayout(new BorderLayout());
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(null);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		mainPanel.add(makeIDEToggleShowingDemo("eclipse", IDE.ECLIPSE, GlobalSettings.showEclipse, b -> GlobalSettings.showEclipse = b));
		mainPanel.add(makeIDEToggleShowingDemo("intellij", IDE.IDEA, GlobalSettings.showIntelliJ, b -> GlobalSettings.showIntelliJ = b));
		mainPanel.add(new JLabel("  "));
		mainPanel.add(makeToggleShowingDemo("win", GlobalSettings.showWin, b -> GlobalSettings.showWin = b));
		mainPanel.add(makeToggleShowingDemo("mac", GlobalSettings.showMac, b -> GlobalSettings.showMac = b));
		mainPanel.add(new JLabel("  "));
		mainPanel.add(makeToggleShowingDemo("rabbit", GlobalSettings.fast, b -> GlobalSettings.fast = b));
		
		JLabel websiteLabel = new JLabel("     VictorRentea.ro    ", SwingConstants.CENTER);
		websiteLabel.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			
			public void mousePressed(MouseEvent e) {
			}
			
			public void mouseExited(MouseEvent e) {
			}
			
			public void mouseEntered(MouseEvent e) {
			}
			@SneakyThrows
			public void mouseClicked(MouseEvent e) {
				openWebpage(new URI("http://victorrentea.ro"));
			}
		});
		mainPanel.add(websiteLabel);
		
		mainPanel.add(makeToggle("timer", GlobalSettings.timerVisible, this::toggleTimer));
		JButton closeButton = new JButton(" X ");
		closeButton.setMargin(new Insets(0, 0, 0, 0));
		closeButton.setOpaque(true);
		closeButton.addActionListener(e -> System.exit(0));
		mainPanel.add(closeButton);
		
		setOpacity(0.5f);

		addMouseListener(new OpaqueOnHoverMouseListener(this, 0.5f));
		for(Component c:mainPanel.getComponents()) {
			c.addMouseListener(new OpaqueOnHoverMouseListener(this, 0.5f));
		}
		setVisible(true);
	}
	
	public void tick() {
		horizontalPositioner.tick();
	}
	
	private void toggleTimer(boolean newState) {
		if (newState) {
			ShortcutKeylogger.timerFrame.display();
		} else {
			ShortcutKeylogger.timerFrame.setVisible(false);
		}
	}
	
	private JToggleButton makeIDEToggleShowingDemo(String ideLabel, IDE ide, boolean initial, Consumer<Boolean> action) {
		JToggleButton button = makeToggleShowingDemo(ideLabel, initial, action);
		JPopupMenu popup = new JPopupMenu();
		JMenuItem item = new JMenuItem("Listen to shortcuts of this");
		item.addActionListener(e-> {
			GlobalSettings.workingIde = ide;
			ShortcutKeylogger.reloadShortcutsForCurrentIDE();
		});
		popup.add(item);
		button.addMouseListener(new PopClickListener(popup));
		return button;
	}
	private JToggleButton makeToggleShowingDemo(String ideLabel, boolean initial, Consumer<Boolean> action) {
		return makeToggle(ideLabel, initial, b -> {
			action.accept(b);
			ShortcutKeylogger.toastrColumn.showToastr(Shortcut.DEMO);
		});
	}
	private JToggleButton makeToggle(String ideLabel, boolean initial, Consumer<Boolean> action) {
		JToggleButton button = new JToggleButton(createImageIcon("/"+ideLabel+"18.png", ideLabel));
		button.setSelected(initial);
		button.addChangeListener(new ChangeListener() {
			boolean oldState = initial;
			public void stateChanged(ChangeEvent e) {
				if (button.isSelected() != oldState) {
					action.accept(button.isSelected());
					oldState = !oldState;
				}
			}
		}
		); 
		button.setMargin(new Insets(0, 0, 0, 0));
		return button;
	}
	
	public ImageIcon createImageIcon(String path, String description) {
		return new ImageIcon(getClass().getResource(path), description);
	}
	
	public static boolean openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	            return true;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return false;
	}


}