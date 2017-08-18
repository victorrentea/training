package victor.training.oo.creational.factory.blocks.spi;

public interface Cube {
	void stackOnto(Cube anotherCube);
	void putOn(Board board);
}
