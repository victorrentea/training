package victor.keys.logger;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

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
}
