package victor.training.oo.behavioral.strategy.customs;

public class CustomsService {

	public double computeAddedCustomsTax(String originCountry, double tobacoValue, double otherValue) { // UGLY API we CANNOT change
//		switch (originCountry) { // INITIAL(
//		case "UK": return tobacoValue/2 + otherValue/2; 
//		case "CH": return tobacoValue + otherValue;
//		case "FR": 
//		case "ES": // other EU country codes...
//		case "RO": return tobacoValue/3;
//		default: throw new IllegalArgumentException("Not a valid country ISO2 code: " + originCountry);
//		} // INITIAL)
		// SOLUTION(
		CustomsTaxCalculator taxCalculator = getTaxCalculatorFor(originCountry); 
		return taxCalculator.forTaboco(tobacoValue) + taxCalculator.forOther(otherValue);
		// SOLUTION)
	}
	// SOLUTION (
	public CustomsTaxCalculator getTaxCalculatorFor(String originCountry) {
		switch (originCountry) {
		case "UK": return new UKTax();
		case "CH": return new ChinaTax();
		case "FR": 
		case "ES": // other EU country codes...
		case "RO": return new EUTax();
		default: throw new IllegalArgumentException("Not a valid country ISO2 code: " + originCountry);
		}
	}
	// SOLUTION )
	
	public static void main(String[] args) {
		CustomsService service = new CustomsService();
		System.out.println("Tax for (RO,100,100) = " + service.computeAddedCustomsTax("RO", 100, 100));
		System.out.println("Tax for (CH,100,100) = " + service.computeAddedCustomsTax("CH", 100, 100));
		System.out.println("Tax for (UK,100,100) = " + service.computeAddedCustomsTax("UK", 100, 100));
	}
}
