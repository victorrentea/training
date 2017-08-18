package victor.training.oo.creational.singleton;

public class SingletonPlay {

	public static void main(String[] args) {
		// INITIAL(
//		System.out.println("Configuration setting a = " + new AppConfiguration().getProperty("a")); 
//		System.out.println("Configuration setting b = " + new AppConfiguration().getProperty("b")); 
//		System.out.println("Configuration setting a = " + new AppConfiguration().getProperty("a")); 
		// INITIAL)

		// SOLUTION(
		System.out.println("Configuration setting a = " + AppConfiguration.getInstance().getProperty("a")); 
		System.out.println("Configuration setting b = " + AppConfiguration.getInstance().getProperty("b")); 
		System.out.println("Configuration setting a = " + AppConfiguration.getInstance().getProperty("a")); 
		// SOLUTION)

		// an example of EAGER singleton in JDK
		Runtime.getRuntime().totalMemory();
	}
}
