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

	private String sharedData;

	public String getSharedData() {
		return sharedData;
	}

	public void setSharedData(String data) {
		this.sharedData = data;
	}

}
