package victor.training.oo.creational.factory;

import java.util.ArrayList;
import java.util.List;

import victor.training.oo.creational.factory.spi.FurnitureFactory;

public class Distributor {

	private final FurnitureFactory factory;

	public Distributor(FurnitureFactory factory) {
		this.factory = factory;
	}

	public List<Object> getKitchenFurniture() {
		List<Object> tables = new ArrayList<>();
		tables.add(factory.createTable());
		for (int i = 0; i < 4; i++) {
			tables.add(factory.createChair());
		}
		return tables;
	}
}
