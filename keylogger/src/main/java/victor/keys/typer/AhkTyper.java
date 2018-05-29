package victor.keys.typer;
//package victor.shortcut.keylogger;
//
//import java.awt.Color;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//
//import javax.swing.JFrame;
//
//import org.apache.commons.io.IOUtils;
//
//import lc.kra.system.keyboard.GlobalKeyboardHook;
//import lc.kra.system.keyboard.event.GlobalKeyAdapter;
//import lc.kra.system.keyboard.event.GlobalKeyEvent;
//
//public class AhkTyper {
//	
//	public static JFrame frame;
//	public static Object userPressedKeyEvent = new Object();
//	static String textToWrite ="";
//	static String controlId;
//	static int lastIndex = 0;
//	static String getControlIdScriptPath = new File("getControlId.ahk").getAbsolutePath();
//	static String sendAhkScriptPath = new File("send.ahk").getAbsolutePath();
//	
//	private static void makeUI() {
//		frame = new JFrame("Keyboard Shortcuts Pane");
//		frame.setUndecorated(true);
//		frame.setBackground(new Color(1, 1, 1, 0f));
//
////		frame.setFocusableWindowState(false);
//		frame.setSize(100, 30);
//		frame.setAlwaysOnTop(true);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setFocusableWindowState(false);
//		frame.setFocusable(true);
//		frame.setVisible(false);
//	}
//	
//	public static void pollForKeys() {
//		while (true) {
//			synchronized (userPressedKeyEvent) {
//				try {
//					userPressedKeyEvent.wait();
//				} catch (InterruptedException e) {
//					throw new RuntimeException(e);
//				}
//			}
//			int nextIndex = Math.min(textToWrite.length(), lastIndex +10);
//			ahkSend(toAhkKeys(textToWrite.substring(lastIndex, nextIndex)));
//			lastIndex = nextIndex;
//			if (lastIndex == textToWrite.length()) {
//				releaseFocus();
//			}
//		}
//	}
//
//	public static void main(String[] args) throws IOException {
//		makeUI();
//		
//		// might throw a UnsatisfiedLinkError if the native library fails to load or a RuntimeException if hooking fails 
//		
//		new Thread(AhkTyper::pollForKeys).start();
//		
//	
//		GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook();
//		keyboardHook.addKeyListener(new GlobalKeyAdapter() {
//			
//			@Override public void keyPressed(GlobalKeyEvent event) {
//		
//				if (event.isControlPressed() && 
//					event.isShiftPressed() && 
//					event.getVirtualKeyCode() >= GlobalKeyEvent.VK_1 &&
//					event.getVirtualKeyCode() <= GlobalKeyEvent.VK_9) {
//					
//					int snippetId = event.getVirtualKeyCode()-GlobalKeyEvent.VK_1 + 1;
//					initializeSnippet(snippetId);
//					try {
//						
//						Process process = Runtime.getRuntime().exec(new String[]{"autohotkey.exe", getControlIdScriptPath});
//						controlId = IOUtils.toString(process.getInputStream(), "UTF-8");
//						System.out.println("Control ID: " + controlId);
//						grabFocus();
//					} catch (IOException e) {
//						throw new RuntimeException(e);
//					}
//					return;
//				}
//				
//				if (lastIndex == textToWrite.length()) {
//					return;
//				}
//				
//				synchronized(userPressedKeyEvent) {
//					userPressedKeyEvent.notifyAll();
//				}
//
//				if(event.getVirtualKeyCode()==GlobalKeyEvent.VK_ESCAPE  && event.isShiftPressed()) {
//					System.out.println("Terminated");
//					System.exit(0);
//				}
//			}
//			
//			private void initializeSnippet(int snippetId) {
//				String fileName = ((int)snippetId) + ".txt";					
//				try (FileReader fileReader = new FileReader(fileName)) {
//					textToWrite = IOUtils.toString(fileReader).replace("\r", "");
//				} catch (IOException e) {
//					throw new RuntimeException(e);
//				}
//				System.out.println("Loaded: " + textToWrite.substring(0, textToWrite.indexOf('\n')));
//				lastIndex = 0;
//			}
//			@Override public void keyReleased(GlobalKeyEvent event) {
////				 System.out.println(event); 
////				System.out.println("Released " + event.getKeyChar());
//			}
//		});
//
//		System.out.println("Global keyboard hook successfully started, press [shift-escape] key to shutdown.");
//		try {
//			while(true) Thread.sleep(128);
//		} catch(InterruptedException e) { /* nothing to do here */ }
//		  finally { keyboardHook.shutdownHook(); }
//	}
//	
//	private static void ahkSend(String text) {
//		try {
//			long t0 = System.currentTimeMillis();
////			System.out.println("Sending " + text);
//			Process p = Runtime.getRuntime().exec(new String[]{"autohotkey.exe", sendAhkScriptPath, controlId, text});
//			System.out.println("d1=" +(System.currentTimeMillis()-t0));
//			p.waitFor();
//			System.out.println("d2=" +(System.currentTimeMillis()-t0));
////			Thread.sleep(10);
//		} catch (IOException|InterruptedException e) {
//			throw new RuntimeException(e);
//		}
//		
//	}
//	
//	private static void grabFocus() {
//		frame.toFront();
//		frame.setFocusable(true);
//		frame.setFocusableWindowState(true);
//		frame.setAutoRequestFocus(true);
//		frame.setVisible(true);
//		frame.requestFocus();
//	}
//
//	private static void releaseFocus() {
//		frame.setFocusableWindowState(false);
//		frame.setFocusable(false);
//		frame.setVisible(false);
//	}
//
//	private static String toAhkKeys(String s) {
//		return s
//				.replace("+", "+=")
//				.replace("\"", "+'")
//				.replace("{", "~")
//				.replace("}", "{}}")
//				.replace("~", "{{}");
//	}
//}