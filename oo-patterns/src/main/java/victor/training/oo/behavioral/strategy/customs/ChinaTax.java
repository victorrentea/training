package victor.training.oo.behavioral.strategy.customs; // SOLUTION

public class ChinaTax implements CustomsTaxCalculator {

	public double forTaboco(double value) {
		return value;
	}

	public double forOther(double value) {
		return value;
	}
}
