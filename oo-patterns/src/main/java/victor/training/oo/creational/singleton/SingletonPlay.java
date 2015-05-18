package victor.training.oo.creational.singleton;

public class SingletonPlay {

	public static void main(String[] args) {
		System.out.println("Singleton data: " + Singleton.getInstance().getData());
		Singleton.getInstance().setData("AAA");
		System.out.println("Singleton data: " + Singleton.getInstance().getData());
		Singleton.getInstance().setData("BBB");
		System.out.println("Singleton data: " + Singleton.getInstance().getData());

		// 2 an example of EAGER singleton in JDK
		Runtime.getRuntime().totalMemory();
	}
}
