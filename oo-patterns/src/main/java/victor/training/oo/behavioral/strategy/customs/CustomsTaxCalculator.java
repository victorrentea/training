package victor.training.oo.behavioral.strategy.customs;

// Note! In real life, always use BigDecimal for money! 
public interface CustomsTaxCalculator {
	double forTaboco(double value);
	double forOther(double value);
}
