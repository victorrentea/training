package victor.training.oo.creational.singleton;

public class Singleton {
	private static Singleton INSTANCE;

	private Singleton() {
		System.out.println("Creating singleton instance");
	}

	public static Singleton getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Singleton();
		}
		return INSTANCE;
	}

	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
