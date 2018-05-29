package victor.keys.logger;

import java.util.ArrayList;
import java.util.List;

public class ToastrColumn {

	private List<Toastr> toastrs = new ArrayList<>();

	public synchronized void showToastr(Shortcut shortcut) {
		Toastr newToastr = new Toastr(shortcut, GlobalSettings.fast ? 
				GlobalSettings.FAST_DISPLAY_TIME : GlobalSettings.SLOW_DISPLAY_TIME);
		toastrs.add(0, newToastr);
		for (Toastr toastr : toastrs) {
			toastr.descend();
		}
		ShortcutKeylogger.controls.setVisible(true);
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