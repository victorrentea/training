package victor.keys.typer;

import static java.util.stream.Collectors.toList;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class AhkFastTyper {
	
	static JFrame frame;
	static String sendAhkScriptPath = new File("sendfast.ahk").getAbsolutePath();
	static String getControlIdScriptPath = new File("getControlId.ahk").getAbsolutePath();	
	static Process ahkProcess;
	static File snippetsBaseFolder = new File("C:/workspace/training/talk/functional-patterns-devoxx-uk/src/main/snippet");
	
	private static void makeUI() {
		frame = new JFrame("Keyboard Shortcuts Pane");
		frame.setUndecorated(true);
		frame.setBackground(new Color(1, 1, 1, 0f));

//		frame.setFocusableWindowState(false);
		frame.setSize(100, 30);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setFocusableWindowState(false);
		frame.setFocusable(true);
		frame.setVisible(false);
	}
	
	public static void main(String[] args) throws IOException {
		makeUI();
		
		TimerTask myTask = new TimerTask() {
		    @Override
		    public void run() {
		    	if (ahkProcess != null) {
		    		if (!ahkProcess.isAlive()) {
		    			System.out.println("AHK just died");
		    			try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
		    			System.out.println("Focus released");
		    			releaseFocus();
		    			ahkProcess = null;
		    		}
		    	}
		    }
		};

		new Timer().schedule(myTask, 2000, 2000);
		
		GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook();
		keyboardHook.addKeyListener(new GlobalKeyAdapter() {
			public void keyPressed(GlobalKeyEvent event) {
				try {
					if (event.isControlPressed() && 
						event.isShiftPressed() && 
						event.getVirtualKeyCode() >= GlobalKeyEvent.VK_1 &&
						event.getVirtualKeyCode() <= GlobalKeyEvent.VK_9) {
						
						killAhk();
						
						int snippetIndex = event.getVirtualKeyCode()-GlobalKeyEvent.VK_1 + 1;
							
								
						Process process = Runtime.getRuntime().exec(new String[]{"autohotkey.exe", getControlIdScriptPath});
						String controlDetails = IOUtils.toString(process.getInputStream(), "UTF-8");
						String controlHWND = controlDetails.split("\\s")[3];
						String windowTitle = controlDetails.substring(controlDetails.indexOf(" || "));
						System.out.println("Control Details: " + controlDetails);
						
						String javaFileName = windowTitle.substring(0, windowTitle.lastIndexOf(".java"));
						javaFileName = javaFileName.substring(javaFileName.lastIndexOf("/") + 1);
						
						System.out.println("Editing file name: " + javaFileName);
						if (snippetIndex > 1) {
							javaFileName += "_" + snippetIndex;
						}
						File snippetFile = new File(snippetsBaseFolder,javaFileName + ".txt");
						
						
						List<String> params = new ArrayList<>();
						params.add("autohotkey.exe");
						params.add("/r");
						params.add(sendAhkScriptPath);
						params.add(controlHWND);
						List<String> chunks = SnippetTokenizer.tokenize(snippetFile);
//						System.out.println("Tokens: " + chunks);
						params.addAll(chunks);
	//						System.out.println(params);
						
						ahkProcess = Runtime.getRuntime().exec(params.toArray(new String[0]));
						System.out.println("Started AHK");
						grabFocus();
						return;
					}
					if(event.getVirtualKeyCode()==GlobalKeyEvent.VK_Q  && event.isShiftPressed() && event.isControlPressed()) {
						killAhk();
						releaseFocus();
					}
					
					if(event.getVirtualKeyCode()==GlobalKeyEvent.VK_ESCAPE  && event.isShiftPressed()) {
						killAhk();
						System.out.println("Terminated");
						System.exit(0);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			


			
			@Override 
			public void keyReleased(GlobalKeyEvent event) {
			}
		});

		System.out.println("Global keyboard hook successfully started, press [shift-escape] key to shutdown.");
		try {
			while(true) Thread.sleep(128);
		} catch(InterruptedException e) { /* nothing to do here */ }
		  finally { keyboardHook.shutdownHook(); }
	}
	
	
	
	private static void grabFocus() {
		frame.toFront();
		frame.setFocusable(true);
		frame.setFocusableWindowState(true);
		frame.setAutoRequestFocus(true);
		frame.setVisible(true);
		frame.requestFocus();
		System.out.println("Focus Grabbed");
	}

	private static void releaseFocus() {
		frame.setFocusableWindowState(false);
		frame.setFocusable(false);
		frame.setVisible(false);
	}

	private static void killAhk() {
		if (ahkProcess != null) {
			
//			try (InputStream is = ahkProcess.getInputStream()) {
//				String s = IOUtils.toString(is);
//				System.out.println("Caught indexes: "  + s);
//			} catch (IOException e) {
//				throw new RuntimeException(e);
//			}
			ahkProcess.destroyForcibly();
			ahkProcess = null;
			System.out.println("Killed AHK");
		} else {
			System.out.println("Nothing to kill");
		}
	}
}