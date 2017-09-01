package victor.training.oo.behavioral.visitor;

import java.util.Arrays;
import java.util.List;

import victor.training.oo.behavioral.visitor.model.Circle;
import victor.training.oo.behavioral.visitor.model.Shape;
import victor.training.oo.behavioral.visitor.model.Square;

public class VisitorPlay {

	public static void main(String[] args) {
		List<Shape> shapes = Arrays.asList(
				new Square(10), 
				new Circle(5), 
				new Square(5));

		PerimeterCalculatorVisitor perimiterCalculator = new PerimeterCalculatorVisitor();
		for (Shape shape : shapes) {
			shape.accept(perimiterCalculator);
		}
		System.out.println("Total perimeter: " + perimiterCalculator.getTotal());
		
		// TODO implements a TotalPriceCalculatorVIsitor
		
		// SOLUTION(
		AreaCalculatorVisitor areaCalculator = new AreaCalculatorVisitor();
		for (Shape shape : shapes) {
			shape.accept(areaCalculator);
		}
		System.out.println("Total area: " + areaCalculator.getTotalArea());
		// SOLUTION)

	}

}
