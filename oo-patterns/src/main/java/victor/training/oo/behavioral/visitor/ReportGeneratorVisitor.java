package victor.training.oo.behavioral.visitor;

import victor.training.oo.behavioral.visitor.model.ExternalProduct;
import victor.training.oo.behavioral.visitor.model.RegularProduct;

public class ReportGeneratorVisitor implements ProductVisitor {

	@Override
	public void visit(RegularProduct product) {
		System.out.println(product.getName());
	}

	@Override
	public void visit(ExternalProduct product) {
		System.out.println(product.getName());
	}

}
