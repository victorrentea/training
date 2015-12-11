package victor.training.java7;

import java.util.ArrayList;
import java.util.List;

public class Sugar {
	public static void main(String[] args) {
		diamond();
		stringSwitch("abc");
		binaryLiterals();
	}

	private static void diamond() {
		// List<Integer> list = new ArrayList<Integer>(); // TODO use diamond // INITIAL 
		List<Integer> list = new ArrayList<>(); // SOLUTION
		list.add(1);
	}
	
	private static void stringSwitch(String string) {
//		if ("abc".equals(string)) { // INITIAL(
//			System.out.println("Got A-B-C");
//		} else if ("def".equals(string)) {
//			System.out.println("Got D-E-F"); 
//		} else {
//			System.out.println("??");
//		} // INITIAL)
		
		switch (string) { // SOLUTION(
		case "abc": System.out.println("Got A-B-C"); break;
		case "def": System.out.println("Got D-E-F"); break;
		default: System.out.println("??");
		} // SOLUTION)
	}

	private static void binaryLiterals() {
		int eight = 0b1000;
		System.out.println(eight);
		
		// int _31 = 0x1F; // TODO write in binary // INITIAL
		int _31 = 0b1_1111; // SOLUTION
		System.out.println(_31);
		

		// int _255 = 0xFF; // TODO write in binary // INITIAL
		int _255 = 0b1111_1111; // SOLUTION
		System.out.println(_255);
		// TODO use the _ separator between each 4 bits
	}
}
