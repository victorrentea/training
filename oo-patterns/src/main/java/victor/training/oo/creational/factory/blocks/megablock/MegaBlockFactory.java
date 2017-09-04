package victor.training.oo.creational.factory.blocks.megablock;

import victor.training.oo.creational.factory.blocks.spi.BlockFactory;
import victor.training.oo.creational.factory.blocks.spi.Board;
import victor.training.oo.creational.factory.blocks.spi.Cube;

//public class MegaBlockFactory { // INITIAL
public class MegaBlockFactory implements BlockFactory { // SOLUTION(

	@Override
	public Board createBoard() {
		return new MegaBlockBoard();
	}

	@Override
	public Cube createCube() {
		return new MegaBlockCube();
	}
	// SOLUTION)
	
}
