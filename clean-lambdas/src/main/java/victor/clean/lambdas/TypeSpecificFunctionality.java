package victor.clean.lambdas;

import lombok.Getter;
import lombok.Setter;
import victor.clean.lambdas.Movie.Type;

public class TypeSpecificFunctionality {
	public static void main(String[] args) {
		System.out.println(new Movie().setType(Type.NEW_RELEASE).computePrice(4));
	}
}

class Movie {
	enum Type {
		CHILDREN, REGULAR, NEW_RELEASE
	}
	@Getter @Setter private Type type;
	
	public double computePrice(int daysRented) {
		switch (type) {
		case CHILDREN: return 5;
		case REGULAR: return 8 + 0.3 * (daysRented - 8);
		case NEW_RELEASE: return 10 + 0.5 * daysRented;
		}
		return 0; // ?! Panic!
	}
}
