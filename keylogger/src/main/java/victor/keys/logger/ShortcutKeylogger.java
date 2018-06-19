package victor.keys.logger;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import victor.keys.logger.KeyCombination.FirstKey;
import victor.keys.logger.utils.BlockDuplicateCalls;

public class ShortcutKeylogger {
	
	private static Map<FirstKey, List<KeyCombination>> shortcuts;
	
	public static ControlsFrame controls = new ControlsFrame();
	public static TimerFrame timerFrame = new TimerFrame();
	public static ToastrColumn toastrColumn = new ToastrColumn();
	public static Reminder reminder = new Reminder();
	private static BlockDuplicateCalls deduplicator = new BlockDuplicateCalls();
	// might throw a UnsatisfiedLinkError if the native library fails to load or a RuntimeException if hooking fails 
	private static GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook();
	
	private static List<KeyCombination> partiallyPressed = new ArrayList<>();
	
	public static void main(String[] args) throws IOException, InterruptedException {

		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				SwingUtilities.invokeLater(() -> {
					timerFrame.tick();
					toastrColumn.tick();
					controls.tick();
					reminder.tick();
				});
			}
		}, 0, 10);
		
		System.out.println("Global keyboard hook successfully started, press [shift-escape] key to shutdown.");
		keyboardHook.addKeyListener(new GlobalKeyAdapter() {
			public void keyPressed(GlobalKeyEvent event) {
				handleInterceptedKeyEvent(event);
			}
			public void keyReleased(GlobalKeyEvent event) {}
		});
		
		toastrColumn.showToastr(Shortcut.STARTUP);
		toastrColumn.showToastr(Shortcut.DEMO);
		
		reloadShortcutsForCurrentIDE();
	}

	public static void reloadShortcutsForCurrentIDE() {
		shortcuts = new ShortcutsCsvParser()
				.parse(new File(GlobalSettings.SHORTCUTS_CSV_FILENAME))
				.get(GlobalSettings.workingIde)
				.stream()
				.collect(groupingBy(KeyCombination::getFirstKey));
		toastrColumn.showToastr(Shortcut.makeUsingIDE(GlobalSettings.workingIde));
	}
	
	protected static void handleInterceptedKeyEvent(GlobalKeyEvent event) {
		if (Character.isJavaLetterOrDigit(event.getKeyChar()) ||
				event.getVirtualKeyCode() == GlobalKeyEvent.VK_UP ||
				event.getVirtualKeyCode() == GlobalKeyEvent.VK_DOWN) {
			
			if (!partiallyPressed.isEmpty()) {
				
				partiallyPressed.stream()
					.filter(entry -> entry.secondKey == event.getKeyChar())
					.max(comparing(KeyCombination::getWeight))
					.map(KeyCombination::getShortcut)
					.ifPresent(ShortcutKeylogger::show);
				partiallyPressed.clear();
			}
			
			FirstKey firstKey = new FirstKey(
					event.isControlPressed(), 
					event.isMenuPressed(), 
					event.isShiftPressed(), 
					event.getVirtualKeyCode());
			
			List<KeyCombination> matchingFirstKey = shortcuts.get(firstKey);
			if (matchingFirstKey != null) {
//				System.out.println("Matching first: " + matchingFirstKey);
				matchingFirstKey.stream()
					.filter(KeyCombination::isSingleChar)
					.findAny()
					.map(KeyCombination::getShortcut)
					.ifPresent(ShortcutKeylogger::show);
				
				partiallyPressed = matchingFirstKey.stream()
						.filter(KeyCombination::isTwoChar)
						.collect(toList());
//				System.out.println("New partially pressed: " + partiallyPressed);
			}
		}
			
//		System.out.println(event + " " + event.getKeyChar());
//		if(event.getVirtualKeyCode()==GlobalKeyEvent.VK_ESCAPE && event.isShiftPressed()) {
//			keyboardHook.shutdownHook();
//			System.out.println("Terminated");
//			System.exit(0);
//		}
		if(event.getVirtualKeyCode()==GlobalKeyEvent.VK_TAB 
				&& !event.isControlPressed() 
				&& event.isMenuPressed()) {
			controls.setVisible(false);
			controls.repaint();
		}
	}

	private static void show(Shortcut shortcut) {
		deduplicator.call(t -> 
			SwingUtilities.invokeLater(() -> toastrColumn.showToastr(t)), 
			shortcut);
	}
	
}