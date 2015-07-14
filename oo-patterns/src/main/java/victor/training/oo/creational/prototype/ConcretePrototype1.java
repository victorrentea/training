package victor.training.oo.creational.prototype;

public class ConcretePrototype1 extends Prototype {
	// TODO expose clone
	// SOLUTION(
	@Override
	public ConcretePrototype1 clone() {
		return (ConcretePrototype1) super.clone();
	}
	// SOLUTION)
}
