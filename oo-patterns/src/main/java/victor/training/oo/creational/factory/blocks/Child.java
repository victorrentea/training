package victor.training.oo.creational.factory.blocks;

import victor.training.oo.creational.factory.blocks.spi.BlockFactory;
import victor.training.oo.creational.factory.blocks.spi.Board;
import victor.training.oo.creational.factory.blocks.spi.Cube;

public class Child {
	public void playWith(BlockFactory cutie) {
		Board board = cutie.createBoard();
		Cube lastCube = cutie.createCube();
		System.out.println("\"Pun primul cub " + lastCube.getClass().getSimpleName() + " pe tabla " + board.getClass().getSimpleName());
		for (int i = 2; i <= 10; i++) {
			Cube cube = cutie.createCube();
			System.out.println("\"Iau cubul " + i + ": " + cube.getClass().getSimpleName() + " si il pun peste " + lastCube.getClass().getSimpleName());
			cube.stackOnto(lastCube);
			lastCube = cube;
		}
		System.out.println("Gata. Asta voi face inca 6 luni de acum inainte... ;p");
	}
}
