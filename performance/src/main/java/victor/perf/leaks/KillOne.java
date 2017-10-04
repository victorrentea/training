package victor.perf.leaks;

import static victor.perf.ConcurrencyUtil.log;
import static victor.perf.ConcurrencyUtil.sleep2;

import victor.perf.ConcurrencyUtil;

public class KillOne {
	public static synchronized void entryPoint() {
		log("start One.a1()");
		sleep2(3_000);
		KillTwo.internalMethod();
		log("start One.a1()");
	}
	
	public static synchronized void internalMethod() {
		log("start One.b1()");
		sleep2(3_000);
		log("end One.b1()");
	}
}

class KillTwo {
	public static synchronized void entryPoint() {
		ConcurrencyUtil.log("start Two.a2()");
		ConcurrencyUtil.sleep2(3_000);
		KillOne.internalMethod();
		ConcurrencyUtil.log("start Two.a2()");
	}
	public static synchronized void internalMethod() {
		ConcurrencyUtil.log("start Two.b2()");
		ConcurrencyUtil.sleep2(3_000);
		ConcurrencyUtil.log("end Two.b2()");
	}
}