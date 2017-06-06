package victor.training.oo.creational.singleton;

public class SingletonPlay {

	public static void main(String[] args) {
		// TODO Apply the singleton pattern to the Singleton class 
//		System.out.println("Singleton data: " + new Singleton().getSharedData()); // INITIAL
//		new Singleton().setSharedData("AAA");  // INITIAL
//		System.out.println("Singleton data: " + new Singleton().getSharedData()); // INITIAL
//		new Singleton().setSharedData("BBB"); // INITIAL
//		System.out.println("Singleton data: " + new Singleton().getSharedData()); // INITIAL

		// SOLUTION(
		System.out.println("Singleton data: " + Singleton.getInstance().getSharedData());
		Singleton.getInstance().setSharedData("AAA"); 
		System.out.println("Singleton data: " + Singleton.getInstance().getSharedData());
		Singleton.getInstance().setSharedData("BBB");
		System.out.println("Singleton data: " + Singleton.getInstance().getSharedData());
		// SOLUTION)

		// 2 an example of EAGER singleton in JDK
		Runtime.getRuntime().totalMemory();
	}
}
