package victor.keys.logger;

import java.util.ArrayList;
import java.util.List;

public class ToastrColumn {

	private List<Toastr> toastrs = new ArrayList<>();

	public synchronized void showToastr2() {
		Toastr newToastr = Toastr.makeWebinarNotification(getDuration());
		addToastr(newToastr);
	}
	
	public synchronized void showToastr(Shortcut shortcut) {
		Toastr newToastr = Toastr.makeShortcut(shortcut, getDuration());
		addToastr(newToastr);
	}
	private void addToastr(Toastr newToastr) {
		toastrs.add(0, newToastr);
		for (Toastr toastr : toastrs) {
			toastr.descend();
		}
		ShortcutKeylogger.controls.setVisible(true);
	}
	private int getDuration() {
		return GlobalSettings.fast ? 
				GlobalSettings.FAST_DISPLAY_TIME : GlobalSettings.SLOW_DISPLAY_TIME;
	}

	public synchronized void tick() {
		if (toastrs.isEmpty()) {
			return;
		}
		for (Toastr toastr : toastrs) {
			toastr.tick();
		}
		Toastr oldest = toastrs.get(toastrs.size() - 1);
		if (oldest.hasDissapeared()) {
			oldest.dispose();
			toastrs.remove(oldest);
		}
	}

}