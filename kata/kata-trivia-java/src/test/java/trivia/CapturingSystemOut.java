package trivia;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CapturingSystemOut {

	public static void main(String[] args) {
		String capture = captureOutput(() -> blackMethod());
		System.err.println("Capured:\n" + capture);
	}
	
	public static String captureOutput(Runnable target) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream originalSysOut = System.out;
		try (PrintStream printStream = new PrintStream(baos)) {
			System.setOut(printStream);
			target.run();
		} finally {
			System.setOut(originalSysOut);
		}
		return baos.toString();
	}

	private static void blackMethod() {
		// snakes and scorpions stray here
		System.out.println("Print business result");
		System.out.println("Print business result2");
		// other weird stuff you don't want to touch
	}
}
