package victor.training.oo.creational.factorymethod;

import javax.xml.parsers.DocumentBuilderFactory;

public class StaticFactoryMethod {

	public static class NamedConstructors {
		private int obscureState;
		
		private NamedConstructors() {
		}
		
		public static NamedConstructors newObjectA() {
			NamedConstructors instance = new NamedConstructors();
			instance.obscureState = 33;
			return instance;
		}
		
		// TODO public static NamedConstructors newObjectB() { 
		public static NamedConstructors newObjectB() { // SOLUTION(
			NamedConstructors instance = new NamedConstructors(); 
			instance.obscureState = 55;
			return instance; 
		} // SOLUTION)
		
	}
	public static void main(String[] args) {
		NamedConstructors a = NamedConstructors.newObjectA();
		// TODO ObjectB has obscure state = 55 
		// NamedConstructors b =
		NamedConstructors b = NamedConstructors.newObjectB(); // SOLUTION
		
		
		// Locate available library in classpath:
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		System.out.println("DBF: " + dbf);
	}
}
