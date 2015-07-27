package victor.training.oo.creational.singleton;

public class SingletonPlay {

	public static void main(String[] args) {
		// TODO Apply the singleton pattern to the Singleton class 
//		System.out.println("Singleton data: " + new Singleton().getData()); // INITIAL
//		new Singleton().setData("AAA");  // INITIAL
//		System.out.println("Singleton data: " + new Singleton().getData()); // INITIAL
//		new Singleton().setData("BBB"); // INITIAL
//		System.out.println("Singleton data: " + new Singleton().getData()); // INITIAL

		// SOLUTION(
		System.out.println("Singleton data: " + Singleton.getInstance().getData());
		Singleton.getInstance().setData("AAA"); 
		System.out.println("Singleton data: " + Singleton.getInstance().getData());
		Singleton.getInstance().setData("BBB");
		System.out.println("Singleton data: " + Singleton.getInstance().getData());
		// SOLUTION)

		// 2 an example of EAGER singleton in JDK
		Runtime.getRuntime().totalMemory();
	}
}
