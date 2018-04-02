/**
 * Copyright (c) 2016 Kristian Kraljic
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package victor.shortcut.keylogger;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class ShortcutKeylogger {
	
	public static class KeyboardShortcut {
		public final boolean shift;
		public final boolean alt;
		public final boolean ctrl;
		public final String key;
		public final Character secondKey;
		public final String eclipse;
		public final String idea;
		public final String description;
		
		public Character getSecondKey() {
			return secondKey;
		}
		public String getDescription() {
			return description;
		}
		
		public KeyboardShortcut(String shortcut,String eclipseShortcut, String description) {
			eclipse=eclipseShortcut;
			shift = shortcut.toUpperCase().contains("SHIFT");
			alt = shortcut.toUpperCase().contains("ALT");
			ctrl = shortcut.toUpperCase().contains("CTRL");
			String beforeComma = shortcut.split(",")[0];
			key = beforeComma.substring(beforeComma.lastIndexOf('-') + 1);
			secondKey = shortcut.contains(",") ? Character.toLowerCase(shortcut.split(",")[1].charAt(0)) : null;
			idea = description.split("\\|")[0];
			this.description = description.split("\\|")[1];
		}
		
		public boolean isSingleChar() {
			return secondKey == null;
		}
		public boolean isTwoChar() {
			return secondKey != null;
		}
		
		public int getWeight() {
			return (shift?1:0) + 
					(ctrl?1:0) +
					(alt?1:0);					
		}
		
		public boolean matchesFirstChar(GlobalKeyEvent event) {
			if (shift != event.isShiftPressed()) return false;
			if (ctrl != event.isControlPressed()) return false;
			if (alt != event.isMenuPressed()) return false;
			if ("UP".equals(key)) return event.getVirtualKeyCode() == GlobalKeyEvent.VK_UP;
			if ("DOWN".equals(key)) return event.getVirtualKeyCode() == GlobalKeyEvent.VK_DOWN;
			if ("INS".equals(key)) return event.getVirtualKeyCode() == GlobalKeyEvent.VK_INSERT;
			if (key.matches("F\\d\\d?")) {
				int fx = Integer.parseInt(key.substring(1));
				return event.getVirtualKeyCode() == GlobalKeyEvent.VK_F1 -1 +fx;
			}
			return Character.toLowerCase(key.charAt(0)) == Character.toLowerCase(event.getKeyChar());
		}

		public String toText() {
			return eclipse + " - " + description;
		}
	}
	
	private static List<KeyboardShortcut> shortcuts = new ArrayList<>();
	
	private static List<KeyboardShortcut> partiallyPressed = new ArrayList<>();
	
	
	private static boolean run = true;
	
	public static JFrame frame;
	public static DefaultTableModel dm;
	private static JTable table;
	static JScrollPane scroll;
	public static Queue<KeyboardShortcut> lastPrinted = new LinkedBlockingQueue<>();
	static float opacity = 1;
	private static void show(KeyboardShortcut shortcut) {
		SwingUtilities.invokeLater(() -> {
			opacity = 1;
			if (!lastPrinted.contains(shortcut)) {
				lastPrinted.offer(shortcut);
				if (lastPrinted.size() > 3) {
					lastPrinted.poll();
				}
				dm.addRow(new String[]{shortcut.eclipse, shortcut.idea, shortcut.description});
				table.repaint();
			}
			SwingUtilities.invokeLater(() -> {
				JScrollBar vertical = scroll.getVerticalScrollBar();
				vertical.setValue( vertical.getMaximum() );
				scroll.repaint();
				frame.setVisible(true);
			});
		});
	}
	public static void main(String[] args) throws IOException {
		loadShortcuts(new File("shortcuts.properties"));
		
		frame = new JFrame("Keyboard Shortcuts Pane");
		frame.setUndecorated(true);
		frame.setBackground(new Color(1, 1, 1, 0f));
		dm = new DefaultTableModel(0, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		dm.setColumnIdentifiers(new Vector<String>(Arrays.asList("Eclipse", "IDEA", "Shortcut")));
		table = new JTable(dm);
		table.getColumnModel().getColumn(0).setMaxWidth(130);
		table.getColumnModel().getColumn(0).setMinWidth(130);
		table.getColumnModel().getColumn(1).setMaxWidth(130);
		table.getColumnModel().getColumn(1).setMinWidth(130);
		table.setFont(new Font("Calibri", Font.BOLD, 20));
		table.setRowHeight(25);
		dm.addRow(new String[]{"","","> SHIFT-ESC to exit"});
		dm.addRow(new String[]{"","","> CTRL-ALT-SHIFT-DOWN to hide"});
		scroll = new JScrollPane (table, 
				   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getViewport().setOpaque(false);
		scroll.setOpaque(false);
		scroll.setViewportBorder(BorderFactory.createEmptyBorder());
		scroll.setBorder(BorderFactory.createEmptyBorder());
		frame.add(scroll);

		frame.setFocusableWindowState(false);
		frame.setSize(550, 95);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		new Timer(200, e -> {
			SwingUtilities.invokeLater(() -> {
				int w = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getWidth();
				frame.setLocation(w - frame.getWidth() - 120, 0);
				opacity -= 0.01;
				opacity = (float) Math.max(opacity, 0.5);
				table.setBackground(new Color(1, 1, 1, opacity));
				table.setForeground(new Color(0, 0, 0, opacity));
				table.getTableHeader().setBackground(new Color(1, 1, 1, opacity));
				table.getTableHeader().setForeground(new Color(0, 0, 0, opacity));
				frame.repaint();
			});
		}).start();
		
		
		// might throw a UnsatisfiedLinkError if the native library fails to load or a RuntimeException if hooking fails 
		GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook();

		System.out.println("Global keyboard hook successfully started, press [shift-escape] key to shutdown.");
		keyboardHook.addKeyListener(new GlobalKeyAdapter() {
			
			@Override public void keyPressed(GlobalKeyEvent event) {
				if (Character.isJavaLetterOrDigit(event.getKeyChar()) ||
						event.getVirtualKeyCode() == GlobalKeyEvent.VK_UP ||
						event.getVirtualKeyCode() == GlobalKeyEvent.VK_DOWN) {
					if (!partiallyPressed.isEmpty()) {
						partiallyPressed.stream()
						.filter(s -> s.secondKey == event.getKeyChar())
						.max(comparing(KeyboardShortcut::getWeight))
						.ifPresent(ShortcutKeylogger::show);
					}
					
					shortcuts.stream()
						.filter(s -> s.matchesFirstChar(event))
						.filter(KeyboardShortcut::isSingleChar)
						.findAny()
						.ifPresent(ShortcutKeylogger::show);
					
					partiallyPressed = shortcuts.stream()
							.filter(s -> s.matchesFirstChar(event))
							.filter(KeyboardShortcut::isTwoChar)
							.collect(toList());
//					System.out.println("New partially pressed: " + partiallyPressed);
				}
					
//				System.out.println(event + " " + event.getKeyChar());
				if(event.getVirtualKeyCode()==GlobalKeyEvent.VK_ESCAPE  && event.isShiftPressed()) {
					System.out.println("Terminated");
					System.exit(0);
				}
				if(event.getVirtualKeyCode()==GlobalKeyEvent.VK_DOWN 
						&& event.isShiftPressed() && event.isControlPressed() && event.isMenuPressed()) {
					frame.setVisible(false);
					frame.repaint();
				}
				if(event.getVirtualKeyCode()==GlobalKeyEvent.VK_UP
						&& event.isShiftPressed() && event.isControlPressed() && event.isMenuPressed()) {
					frame.setVisible(true);
					frame.repaint();
				}
			}
			@Override public void keyReleased(GlobalKeyEvent event) {
//				 System.out.println(event); 
			}
		});
		
		try {
			while(run) Thread.sleep(128);
		} catch(InterruptedException e) { /* nothing to do here */ }
		  finally { keyboardHook.shutdownHook(); }
	}
	
	private static void loadShortcuts(File file) throws IOException {
		Properties props = new Properties();
		try (FileInputStream fis = new FileInputStream(file)) {
			props.load(fis);
		}
		for (Object propNameObj : Collections.list(props.propertyNames())) {
			String propName = (String) propNameObj;
			KeyboardShortcut eclipseShortcut = new KeyboardShortcut(propName, propName, props.getProperty(propName));
			shortcuts.add(eclipseShortcut);
			if (!eclipseShortcut.idea.isEmpty()) {
				KeyboardShortcut ideaShortcut = new KeyboardShortcut(eclipseShortcut.idea, propName, props.getProperty(propName));
				shortcuts.add(ideaShortcut);
			}
		}		
	}
}