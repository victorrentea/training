package victor.clean.lambdas;

import victor.clean.lambdas.Movie.Type;


class Movie {
	enum Type {
		REGULAR, NEW_RELEASE, CHILDREN
	}
	private final Type type;
	
	public Movie(Type type) {
		this.type = type;
	}

	public int computePrice(int daysRented) {
		switch (type) {
		case REGULAR: return daysRented + 1;
		case NEW_RELEASE: return daysRented * 2;
		case CHILDREN: return 5;
		}
		return 0; // ?!.. Free!! Deducted from your salary!
	}
}




public class E__TypeSpecific_Functionality {
	public static void main(String[] args) {
		System.out.println(new Movie(Type.REGULAR).computePrice(2));
		System.out.println(new Movie(Type.NEW_RELEASE).computePrice(2));
		System.out.println(new Movie(Type.CHILDREN).computePrice(2));
		System.out.println("COMMIT now!");
	}
}


//abstract class Movie {
//	abstract public int computePrice(int daysRented);
//}
//class RegularMovie extends Movie {
//	public int computePrice(int daysRented) {
//		return daysRented + 1;
//	}
//}
//class NewReleaseMovie extends Movie {
//	public int computePrice(int daysRented) {
//		return daysRented * 2;
//	}
//}
//class ChildrenMovie extends Movie {
//	public int computePrice(int daysRented) {
//		return 5;
//	}
//}