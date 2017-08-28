package victor.training.oo.stuff;

public class Helper {
	public static void workSomeTime() {
		try {
			for (int i = 0; i< 20; i++) {
				System.out.print(".");
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		System.out.println();
	}
}
