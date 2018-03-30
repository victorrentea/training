package victor.clean.lambdas;

import lombok.Getter;
import lombok.Setter;

public class OptionalVsNPE {

	public static void main(String[] args) {
		Customer customer = new Customer();
		double discount = customer.getProfile().getGoldCard().getDiscount();
		System.out.println("discount:  "+discount);
	}
}

class Customer {
	@Getter @Setter private Profile profile;
}

class Profile {
	@Getter @Setter private GoldCard goldCard;
}

class GoldCard {
	@Getter @Setter private double discount;
}
