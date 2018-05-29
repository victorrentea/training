package victor.keys.typer;
//package victor.shortcut.keylogger;
//
//import java.awt.AWTException;
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.Robot;
//import java.awt.event.KeyEvent;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Vector;
//
//import javax.swing.BorderFactory;
//import javax.swing.JFrame;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.table.DefaultTableModel;
//
//import org.apache.commons.io.IOUtils;
//
//import lc.kra.system.keyboard.GlobalKeyboardHook;
//import lc.kra.system.keyboard.event.GlobalKeyAdapter;
//import lc.kra.system.keyboard.event.GlobalKeyEvent;
//
//public class AutoTyper {
//	
//	
//	private static boolean run = true;
////	
//	public static JFrame frame;
//	public static DefaultTableModel dm;
//	private static JTable table;
//	static JScrollPane scroll;
//	static float opacity = 1;
//
//	public static void main(String[] args) throws IOException {
//		frame = new JFrame("Keyboard Shortcuts Pane");
//		frame.setUndecorated(true);
//		frame.setBackground(new Color(1, 1, 1, 0f));
//		dm = new DefaultTableModel(0, 0) {
//			@Override
//			public boolean isCellEditable(int row, int column) {
//				return false;
//			}
//		};
//		dm.setColumnIdentifiers(new Vector<String>(Arrays.asList("Eclipse", "IDEA", "Shortcut")));
//		table = new JTable(dm);
//		table.getColumnModel().getColumn(0).setMaxWidth(130);
//		table.getColumnModel().getColumn(0).setMinWidth(130);
//		table.getColumnModel().getColumn(1).setMaxWidth(130);
//		table.getColumnModel().getColumn(1).setMinWidth(130);
//		table.setFont(new Font("Calibri", Font.BOLD, 20));
//		table.setRowHeight(25);
//		dm.addRow(new String[]{"","","> SHIFT-ESC to exit"});
//		dm.addRow(new String[]{"","","> CTRL-ALT-SHIFT-DOWN to hide"});
//		scroll = new JScrollPane (table, 
//				   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		scroll.getViewport().setOpaque(false);
//		scroll.setOpaque(false);
//		scroll.setViewportBorder(BorderFactory.createEmptyBorder());
//		scroll.setBorder(BorderFactory.createEmptyBorder());
//		frame.add(scroll);
//
//		frame.setFocusableWindowState(false);
//		frame.setSize(550, 95);
//		frame.setAlwaysOnTop(true);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
//		
//		
//		// might throw a UnsatisfiedLinkError if the native library fails to load or a RuntimeException if hooking fails 
//		GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook();
//		
//		
//		
//		
//	
//		System.out.println("Global keyboard hook successfully started, press [shift-escape] key to shutdown.");
//		keyboardHook.addKeyListener(new GlobalKeyAdapter() {
//			String textToWrite ="";
//			int toDelete=0, toWrite=0;
//			int lastIndex = 0;
//			@Override public void keyPressed(GlobalKeyEvent event) {
//			
//				if (event.isControlPressed() && 
//					event.isShiftPressed() && 
//					event.getVirtualKeyCode() >= GlobalKeyEvent.VK_1 &&
//					event.getVirtualKeyCode() <= GlobalKeyEvent.VK_9) {
//					String fileName = ((int)(event.getVirtualKeyCode()-GlobalKeyEvent.VK_1 + 1)) + ".txt";					
//					try (FileReader fileReader = new FileReader(fileName)) {
//						textToWrite = IOUtils.toString(fileReader).replace("\r", "");
//					} catch (IOException e) {
//						throw new RuntimeException(e);
//					}
//					System.out.println("Loaded: " + textToWrite.substring(0, textToWrite.indexOf('\n')));
//					lastIndex = 0;
//					return;
//				}
//				
//				if (lastIndex == textToWrite.length()) {
//					return;
//				}
////				System.out.println("" + event.getVirtualKeyCode() + " -- " + event.getKeyChar() + "(" + ((int)event.getKeyChar())+")");
//				char typedChar = event.getKeyChar();
//				if (event.isShiftPressed()) {
//					typedChar = applyShift(typedChar);
//				}
//				if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_RETURN) {
//					typedChar = '\n';
//				}
////				System.out.print("" + typedChar);//+"("+((int)event.getKeyChar())+")");
//				if (typedChar == 0) return;
//				String charToPrint = typedChar + ":" + ((int)typedChar);
//				if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_BACK) {
//					charToPrint = "BACK";
//				}
//				System.out.println(" " + charToPrint);
////				System.out.println("Pressed " + event.getKeyChar());
//				if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_BACK) {					
////					toDelete --;
////					if (toDelete > 0) {
////						type(KeyEvent.VK_BACK_SPACE);
////						return;
////					} else { 
//						toWrite++;
//						toWrite++;
//						toWrite++;
////					}
//				} else if (typedChar != textToWrite.charAt(lastIndex)) {
////					System.out.println("\n" + ((int)typedChar) + " but expected " + ((int)textToWrite.charAt(lastIndex)));
////					toDelete ++;
//					type(KeyEvent.VK_BACK_SPACE);
//				} else {
//					lastIndex++;
//				}
//
//				if (toWrite>0 && lastIndex < textToWrite.length()) {
//					toWrite --;
////					System.out.println("Typing " + ((int)textToWrite.charAt(lastIndex)));
//					System.out.println(textToWrite.charAt(lastIndex) + " ");
//					RobotTyper.typeKey(textToWrite.charAt(lastIndex));
//				}
//
//					
//				//				System.out.println(event + " " + event.getKeyChar());
//				if(event.getVirtualKeyCode()==GlobalKeyEvent.VK_ESCAPE  && event.isShiftPressed()) {
//					System.out.println("Terminated");
//					System.exit(0);
//				}
//			}
//			private char applyShift(char typedChar) {
//				typedChar = Character.toUpperCase(typedChar);
//				switch (typedChar) {
//				case '8': typedChar = '*';break;
//				case '9': typedChar = '(';break;
//				case '0': typedChar = ')';break;
//				case '[': typedChar = '{';break;
//				case ']': typedChar = '}';break;
//				case '\'': typedChar = '"';break;
//				case '-': typedChar = '_';break;
//				case '2': typedChar = '@';break;
//				case ',': typedChar = '<';break;
//				case '.': typedChar = '>';break;
//				case '=': typedChar = '+';break;
//				}
//				return typedChar;
//			}
//			@Override public void keyReleased(GlobalKeyEvent event) {
////				 System.out.println(event); 
////				System.out.println("Released " + event.getKeyChar());
//			}
//		});
//
//		try {
//			while(run) Thread.sleep(128);
//		} catch(InterruptedException e) { /* nothing to do here */ }
//		  finally { keyboardHook.shutdownHook(); }
//	}
//	
//	static Robot robot;
//	static {
//		try {
//			robot = new Robot();
//			robot.setAutoDelay(3);
//		    robot.setAutoWaitForIdle(true);
//		} catch (AWTException e) {
//			throw new RuntimeException(e);
//		}
//	}
//	
//	
//	
//	 static private void type(int i)
//	  {
//	    robot.delay(3);
//	    robot.keyPress(i);
//	    robot.keyRelease(i);
//	  }
//
//}