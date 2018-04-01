package victor.clean.lambdas;

public class OptionalVsNPE {

	public static void main(String[] args) {
		Customer customer = new Customer();
		double discount = customer.getProfile().getGoldCard().getDiscount();
		System.out.println("discount:  "+discount);
	}
}

class Customer {
	private Profile profile;

	public Profile getProfile() {
		return profile;
	}
}

class Profile {
	private GoldCard goldCard;

	public GoldCard getGoldCard() {
		return goldCard;
	}
	
}

class GoldCard {
	private double discount;
	public double getDiscount() {
		return discount;
	}
}
