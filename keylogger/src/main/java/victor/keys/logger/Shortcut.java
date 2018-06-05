package victor.keys.logger;

import lombok.Data;
import victor.keys.logger.utils.DetermineActiveIDE.IDE;

@Data
public class Shortcut {

	private final String title;
	
	private final String eclipseWin, eclipseMac, ideaWin, ideaMac;
	
	public static final Shortcut DEMO = new Shortcut("Operation Title", 
			"Eclipse on Win", "Eclipse on Mac", 
			"IntelliJ on Win", "IntelliJ on Mac"
			);

	public static final Shortcut STARTUP = new Shortcut("Welcome",
			"Shift-Esc to exit", null, 
			null, null
			);
	
	public static Shortcut makeUsingIDE(IDE ide) {
		return  new Shortcut("Listening " + ide.name(),
				"Right click IDE ",null, 
				null, null
				);
	}
}
