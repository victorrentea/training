package victor.training.java8.voxxed.order.entity;

public class Product {

	private String name;

	public Product(String name) {
		this.name = name;
	}
	
	public final String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "Product{name="+name+"}";
	}
	
}
