package victor.training.oo.behavioral.strategy.customs;// SOLUTION

public class EUTax implements CustomsTaxCalculator {

	public double forTaboco(double value) {
		return value / 3;
	}

	public double forOther(double value) {
		return 0;
	}

}
