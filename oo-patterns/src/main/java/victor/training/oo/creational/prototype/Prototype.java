package victor.training.oo.creational.prototype;

public abstract class Prototype implements Cloneable {
	public Prototype clone() {
		try {
			return (Prototype) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}
