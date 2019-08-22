package victor.training.jpa.app.util;

public class MyUtil {

	public static void staticMethodThrowingException() {
		throw new RuntimeException("Thrown from an Util function. Static functions CANNOT be aspected/intercepted by Spring. Thus--> the incoming Tx is not (yet) made zombie");
	}

//	public static String getUserOnCurrentThread() {
//		// SOLUTION (
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (authentication==null) {
//			return "sys"; // at start-up, there is no user logged in
//		} else {
//			return authentication.getName();
//		}
//		// SOLUTION )
//		// return "user1";//TODO in real apps, implemented via a thread-scoped bean/Spring security context // INITIAL
//	}
}
