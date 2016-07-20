package victor.training.oo.behavioral.visitor.model;

import victor.training.oo.behavioral.visitor.ShapeVisitor;

public interface Shape {
	void accept(ShapeVisitor visitor);
}
