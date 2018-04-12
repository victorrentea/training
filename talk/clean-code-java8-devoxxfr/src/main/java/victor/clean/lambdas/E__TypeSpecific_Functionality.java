package victor.clean.lambdas;

//class Movie {
//	enum Type {
//		REGULAR, NEW_RELEASE, CHILDREN
//	}
//	
//	private final Type type;
//	
//	public Movie(Type type) {
//		this.type = type;
//	}
//
//	public int computePrice(int days) {
//		switch (type) {
//		case REGULAR: return days + 1;
//		case NEW_RELEASE: return days * 2;
//		case CHILDREN: return 5;
//		default: throw new IllegalArgumentException(type.name());
//		}
//	}
//}

abstract class Movie {
	public abstract int computePrice(int days);
}
class RegularMovie extends Movie {
	public int computePrice(int days) {
		return days + 1;
	}
	
}
class NewReleaseMovie extends Movie {

	public int computePrice(int days) {
		return days  *2;
	}
	
}
class ChildrenMovie extends Movie {

	public int computePrice(int days) {
		return 5;
	}
	
}



public class E__TypeSpecific_Functionality {
	public static void main(String[] args) {
//		System.out.println(new Movie(Movie.Type.REGULAR).computePrice(2));
//		System.out.println(new Movie(Movie.Type.NEW_RELEASE).computePrice(2));
//		System.out.println(new Movie(Movie.Type.CHILDREN).computePrice(2));
		System.out.println(new RegularMovie().computePrice(2));
		System.out.println(new NewReleaseMovie().computePrice(2));
		System.out.println(new ChildrenMovie().computePrice(2));
		System.out.println("COMMIT now!");
	}
}