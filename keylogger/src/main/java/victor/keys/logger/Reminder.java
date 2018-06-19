package victor.keys.logger;

public class Reminder {

	private static final int REMIND_INTERVAL_SECONDS = 120;
	long lastTime;
	public Reminder() {
		lastTime = System.currentTimeMillis();
	}
	public void tick() {
		if (System.currentTimeMillis() - lastTime > REMIND_INTERVAL_SECONDS * 1000) {
			lastTime = System.currentTimeMillis();
			ShortcutKeylogger.toastrColumn.showToastr2();
		}
	}
}
