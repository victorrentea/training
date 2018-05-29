package victor.keys.logger;

import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lombok.Data;

@Data
public class KeyCombination {
	@Data
	public static class FirstKey {
		public final boolean ctrl;
		public final boolean alt;
		public final boolean shift;
		public final int vkCode;

		public int getWeight() {
			return (shift?1:0) + 
					(ctrl?1:0) +
					(alt?1:0);					
		}
	}
	public final FirstKey firstKey;
	public final Character secondKey;
	public final Shortcut shortcut;
	
	public boolean isSingleChar() {
		return secondKey == null;
	}
	public boolean isTwoChar() {
		return secondKey != null;
	}
	
	public int getWeight() {
		return firstKey.getWeight();
	}

}
