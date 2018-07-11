package victor.training.java8.completable;

import static java.lang.System.currentTimeMillis;

import victor.training.java8.paral.ThreadUtils;

public class CompletableIntro {

	private static int expensiveStepA() {
		ThreadUtils.println("Call web service A...");
		ThreadUtils.sleep(2000);
		ThreadUtils.println("Done A");
		return 2;
	}
	private static int expensiveStepB() {
		ThreadUtils.println("Call web service B...");
		ThreadUtils.sleep(2000);
		ThreadUtils.println("Done B");
		return 3;
	}
	private static void expensiveStepC(int aPlusB) {
		ThreadUtils.println("Call web service C: " + aPlusB);
		ThreadUtils.sleep(2000);
		ThreadUtils.println("Done C");
	}
	
	public static void main(String[] args) {
		long t0 = currentTimeMillis();
		int a = expensiveStepA();
		int b = expensiveStepB();
		expensiveStepC(a+b);
		long t1 = currentTimeMillis();
		ThreadUtils.println("Took "+(t1-t0));
	}
}
