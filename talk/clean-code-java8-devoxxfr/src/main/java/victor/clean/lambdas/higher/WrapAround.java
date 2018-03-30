package victor.clean.lambdas.higher;

public class WrapAround {
	public static void main(String[] args) {
		insertLanguages();
		insertCountries();
	}
	
	
	// System.nanoTime();

	// existing
	public static void insertLanguages() {
		System.out.println("Insert languages");
	}

	public static void insertCountries() {
		System.out.println("Insert countries");
	}
}
