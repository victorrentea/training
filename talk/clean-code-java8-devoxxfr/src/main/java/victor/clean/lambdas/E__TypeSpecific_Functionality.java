package victor.clean.lambdas;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.function.BiFunction;

import lombok.Data;
import victor.clean.lambdas.Movie.Type;

class Movie {
	enum Type {
		REGULAR(PriceService::computeRegularPrice) ,
		NEW_RELEASE(PriceService::computeNewReleasePrice) ,
		CHILDREN(PriceService::computeChildrenPrice)
		;
		public final BiFunction<PriceService, Integer, Integer> priceAlgo;

		private Type(BiFunction<PriceService, Integer, Integer> priceAlgo) {
			this.priceAlgo = priceAlgo;
		}
		
	}

	private final Type type;

	public Movie(Type type) {
		this.type = type;
	}

}

// that silly "2" should be updatable in DB -> we depend on DB
interface NewReleasePriceRepo {
	double getFactor();
}

@Data
class PriceService {
	private final NewReleasePriceRepo repo;
	
	int computeNewReleasePrice(int days) {
		return (int) (days * repo.getFactor());
	}
	
	int computeRegularPrice(int days) {
		return days + 1;
	}
	int computeChildrenPrice(int days) {
		return 5;
	}
	public int computePrice(Movie.Type type, int days) {
		return type.priceAlgo.apply(this, days);
	}
}

public class E__TypeSpecific_Functionality {
	public static void main(String[] args) {
		NewReleasePriceRepo repo = mock(NewReleasePriceRepo.class);
		when(repo.getFactor()).thenReturn(2d);
		PriceService service = new PriceService(repo);
		
		System.out.println(service.computePrice(Type.REGULAR, 2));
		System.out.println(service.computePrice(Type.NEW_RELEASE, 2));
		System.out.println(service.computePrice(Type.CHILDREN, 2));
		System.out.println("COMMIT now!");
	}
}