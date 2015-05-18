package victor.training.oo.behavioral.visitor.model;

import victor.training.oo.behavioral.visitor.ProductVisitor;

public interface Product {
	void accept(ProductVisitor visitor);
}
