package victor.training.oo.behavioral.visitor.model;

import victor.training.oo.behavioral.visitor.ProductVisitor;

public class RegularProduct implements Product {

	private final String name;
	private final long price;

	public RegularProduct(String name, long price) {
		this.name = name;
		this.price = price;
	}

	@Override
	public void accept(ProductVisitor visitor) {
		visitor.visit(this);
	}

	public String getName() {
		return name + " (Regular)";
	}

	public long getPrice() {
		return price;
	}

}
