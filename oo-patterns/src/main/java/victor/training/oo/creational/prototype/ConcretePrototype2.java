package victor.training.oo.creational.prototype;

public class ConcretePrototype2 extends Prototype {
	@Override
	public ConcretePrototype2 clone() {
		return (ConcretePrototype2) super.clone();
	}
}