package victor.training.oo.creational.factory.furniture.valeni;

import victor.training.oo.creational.factory.furniture.spi.Chair;
import victor.training.oo.creational.factory.furniture.spi.FurnitureFactory;
import victor.training.oo.creational.factory.furniture.spi.Table;

public class ValeniFurnitureFactory implements FurnitureFactory {

	@Override
	public Chair createChair() {
		return new ValeniChair();
	}

	@Override
	public Table createTable() {
		return new ValeniTable();
	}

}
