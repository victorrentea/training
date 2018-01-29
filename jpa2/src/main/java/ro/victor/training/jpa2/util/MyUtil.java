package ro.victor.training.jpa2.util;

public class MyUtil {

	public static void staticMethodThrowingException() {
		throw new RuntimeException("Thrown from an Util function. Static functions CANNOT be aspected/intercepted by Spring. Thus--> the incoming Tx is not (yet) made zombie");
	}
}
