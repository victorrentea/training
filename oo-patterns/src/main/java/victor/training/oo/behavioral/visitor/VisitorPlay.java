package victor.training.oo.behavioral.visitor;

import java.util.Arrays;
import java.util.List;

import victor.training.oo.behavioral.visitor.model.ExternalProduct;
import victor.training.oo.behavioral.visitor.model.Product;
import victor.training.oo.behavioral.visitor.model.RegularProduct;

public class VisitorPlay {

	public static void main(String[] args) {
		List<Product> productList = Arrays.asList(
				new RegularProduct("Table",10), 
				new ExternalProduct("Chair", 5), 
				new RegularProduct("Sofa", 23));

		ReportGeneratorVisitor reportVisitor = new ReportGeneratorVisitor();
		for (Product product : productList) {
			product.accept(reportVisitor);
		}
		
		// TODO implements a TotalPriceCalculatorVIsitor
		
		// SOLUTION(
		TotalPriceCalculatorVisitor totalVisitor = new TotalPriceCalculatorVisitor();
		for (Product product : productList) {
			product.accept(totalVisitor);
		}
		System.out.println("Total price: " + totalVisitor.getTotal());
		// SOLUTION)

	}

}
