package victor.training.java8.voxxed.more.patterns;

import java.util.function.DoubleUnaryOperator;
import java.util.stream.Stream;

public class Decorator {
	public static class DefaultSalaryCalculator implements DoubleUnaryOperator {
	    @Override
	    public double applyAsDouble(double grossAnnual) {
	        return grossAnnual / 12;
	    }
	}
	
	public static class Taxes {
		public static double generalTax(double d) {
			return d*(1-18/100d);
		}
		public static double socialSecurityTax(double d) {
			return d * (1-2/100d);
		}
		public static double healthInsurance(double d) {
			return d * (1-5/100d);
		}
	}
	
	public static void main(String[] args) {
		double netSalary = new DefaultSalaryCalculator()
		        .andThen( Taxes::generalTax )
		        .andThen( Taxes::socialSecurityTax ) 
		        .andThen( Taxes::healthInsurance )
		        .applyAsDouble( 30000.00 );
		
		// or ... 
		
		double netSalary2 = calculate( 30000.00, 
                new DefaultSalaryCalculator(), 
                Taxes::generalTax, 
                Taxes::socialSecurityTax, 
                Taxes::healthInsurance );
	}
	
	public static double calculate(double gross, DoubleUnaryOperator... fs) {
	    return Stream.of( fs )
	                 .reduce( DoubleUnaryOperator.identity(), 
	                          DoubleUnaryOperator::andThen )
	                 .applyAsDouble( gross );
	}
}
