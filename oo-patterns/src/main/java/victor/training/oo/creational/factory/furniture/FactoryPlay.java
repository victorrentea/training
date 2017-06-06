package victor.training.oo.creational.factory.furniture;

import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import victor.training.oo.creational.factory.furniture.spi.FurnitureFactory;
import victor.training.oo.creational.factory.furniture.valeni.ValeniFurnitureFactory;

public class FactoryPlay {

	public static void main(String[] args) {
		FurnitureFactory factory = new ValeniFurnitureFactory();

		Distributor distributor = new Distributor(factory);

		List<Object> kitchen = distributor.getKitchenFurniture();

		System.out.println("Delivered objects: " + kitchen);

		// 2
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
	}
}
