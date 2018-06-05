package victor.keys.logger;

import java.awt.Color;

import victor.keys.logger.utils.DetermineActiveIDE.IDE;

public class GlobalSettings {
	public static final int WIDTH = 300;

	public static final long BLOCK_DUPLICATES_MILLIS = 9000;
	public static final int FAST_DISPLAY_TIME = 3000;
	public static final int SLOW_DISPLAY_TIME = 10000;

	public static final int PIXELS_TO_RIGHT = 80;
	public static final String SHORTCUTS_CSV_FILENAME = "./shortcuts.csv";

	public static final float TOASTR_BASE_OPACITY = 1f;

//	public static final Color TOASTR_COLOR = new Color(81, 163, 81);
	public static final Color TOASTR_BG_COLOR = Color.white;
//	public static final Color TOASTR_FONT_COLOR = new Color(174, 202, 174);
	public static final Color TOASTR_FONT_COLOR = Color.lightGray;

	public static final long DEFAULT_TIMER_MILLIS = 7 * 60 * 1000;

	public static IDE workingIde = IDE.ECLIPSE;

	
	static boolean showEclipse = true;
	static boolean showIntelliJ = true;
	static boolean showWin = true;
	static boolean showMac = false;
	static boolean fast = false;
	static boolean timerVisible = false;

}