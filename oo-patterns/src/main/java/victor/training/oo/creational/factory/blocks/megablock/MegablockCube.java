package victor.training.oo.creational.factory.blocks.megablock;

import victor.training.oo.creational.factory.blocks.spi.Board;
import victor.training.oo.creational.factory.blocks.spi.Cube;

//public class MegablockCube { // INITIAL
public class MegablockCube implements Cube { // SOLUTION(

	@Override
	public void stackOnto(Cube anotherCube) {
		if (!(anotherCube instanceof MegablockCube)) 
			throw new IllegalArgumentException("Not compatible");
		System.out.println("Stacking instably onto " + anotherCube);
	}

	@Override
	public void putOn(Board board) {
		if (!(board instanceof MegablockBoard)) 
			throw new IllegalArgumentException("Not compatible");
		System.out.println("Putting on rough " + board);
	}
	// SOLUTION)
}
