package victor.training.oo.creational.factory.valeni;

import victor.training.oo.creational.factory.spi.Chair;
import victor.training.oo.creational.factory.spi.FurnitureFactory;
import victor.training.oo.creational.factory.spi.Table;

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
