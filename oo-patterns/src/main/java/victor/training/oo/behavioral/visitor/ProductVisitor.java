package victor.training.oo.behavioral.visitor;

import victor.training.oo.behavioral.visitor.model.ExternalProduct;
import victor.training.oo.behavioral.visitor.model.RegularProduct;

public interface ProductVisitor {
	void visit(RegularProduct product);
	void visit(ExternalProduct product);
}
