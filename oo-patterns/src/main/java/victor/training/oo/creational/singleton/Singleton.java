package victor.training.oo.creational.singleton;

public class Singleton {
	private static Singleton INSTANCE; // SOLUTION

	private Singleton() { // SOLUTION
//	public Singleton() { // INITIAL	
		System.out.println("Creating singleton instance");
	}

	public static Singleton getInstance() { // SOLUTION(
		if (INSTANCE == null) {
			INSTANCE = new Singleton();
		}
		return INSTANCE;
	} // SOLUTION)

	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
