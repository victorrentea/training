package victor.training.java8.functionalpatterns;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Movie {
	enum Type {
		REGULAR, NEW_RELEASE, CHILDREN
	}

	private final Type type;

	public Movie(Type type) {
		this.type = type;
	}

	public int computePrice(int days) {
		switch (type) {
		case REGULAR:
			return days + 1;
		case NEW_RELEASE:
			return days * 2;
		case CHILDREN:
			return 5;
		}
		return 0; // ?!.. Free!! Deducted from your salary!
	}
}

public class E__TypeSpecific_Functionality {
	public static void main(String[] args) {
		System.out.println(new Movie(Movie.Type.REGULAR).computePrice(2));
		System.out.println(new Movie(Movie.Type.NEW_RELEASE).computePrice(2));
		System.out.println(new Movie(Movie.Type.CHILDREN).computePrice(2));
		System.out.println("COMMIT now!");
	}
}
