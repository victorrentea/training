package victor.training.oo.behavioral.visitor;

import victor.training.oo.behavioral.visitor.model.ExternalProduct;
import victor.training.oo.behavioral.visitor.model.RegularProduct;

//public class TotalPriceCalculatorVisitor {// implements ... { // INITIAL
public class TotalPriceCalculatorVisitor implements ProductVisitor { // SOLUTION

	private long sum = 0;
	// SOLUTION(

	@Override
	public void visit(RegularProduct product) {
		sum += product.getPrice();
	}

	@Override
	public void visit(ExternalProduct product) {
		sum += product.getExternalPrice();
	}
	// SOLUTION)
	
	public long getTotal() {
		return sum;
	}

}

