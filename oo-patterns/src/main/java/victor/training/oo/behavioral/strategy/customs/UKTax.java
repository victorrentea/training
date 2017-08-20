package victor.training.oo.behavioral.strategy.customs; // SOLUTION

// TODO: Rename to "BrexitTax" ?
public class UKTax implements CustomsTaxCalculator {

	public double forTaboco(double value) {
		return value/2;
	}

	public double forOther(double value) {
		return value/2;
	}
}
