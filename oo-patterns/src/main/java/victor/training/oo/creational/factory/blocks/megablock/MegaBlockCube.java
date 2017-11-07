package victor.training.oo.creational.factory.blocks.megablock;

import victor.training.oo.creational.factory.blocks.spi.Board;
import victor.training.oo.creational.factory.blocks.spi.Cube;

//public class MegaBlockCube { // INITIAL
public class MegaBlockCube implements Cube { // SOLUTION(

	@Override
	public void stackOnto(Cube anotherCube) {
		if (!(anotherCube instanceof MegaBlockCube)) 
			throw new IllegalArgumentException("Not compatible");
		System.out.println("Stacking instably onto " + anotherCube);
	}

	@Override
	public void putOn(Board board) {
		if (!(board instanceof MegaBlockBoard)) 
			throw new IllegalArgumentException("Not compatible");
		System.out.println("Putting on rough " + board);
	}
	// SOLUTION)
}
