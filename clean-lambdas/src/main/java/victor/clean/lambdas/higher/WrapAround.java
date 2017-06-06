package victor.clean.lambdas.higher;

public class WrapAround {
	public static void main(String[] args) {
		insertCustomers();
		insertCountries();
	}
	
	
	// System.nanoTime();

	// existing
	public static void insertCustomers() {
		System.out.println("Insert customers");
	}

	public static void insertCountries() {
		System.out.println("Insert countries");
	}
}
