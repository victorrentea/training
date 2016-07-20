package victor.training.oo.behavioral.visitor;

import victor.training.oo.behavioral.visitor.model.Circle;
import victor.training.oo.behavioral.visitor.model.Square;

//public class AreaCalculatorVisitor {// implements ... { // INITIAL
public class AreaCalculatorVisitor implements ShapeVisitor { // SOLUTION

	private double total = 0;
	// SOLUTION(

	@Override
	public void visit(Square square) {
		total += square.getEdge() * square.getEdge();
	}

	@Override
	public void visit(Circle circle) {
		total += circle.getRadius() * circle.getRadius() * Math.PI;
	}
	// SOLUTION)
	
	public double getTotalArea() {
		return total;
	}

}

