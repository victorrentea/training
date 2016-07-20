package victor.training.oo.behavioral.visitor;

import victor.training.oo.behavioral.visitor.model.Circle;
import victor.training.oo.behavioral.visitor.model.Square;

public interface ShapeVisitor {
	void visit(Square product);
	void visit(Circle product);
}
