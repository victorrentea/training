package victor.clean.lambdas.opt;

public class OptionalVsNPE {

	public static void main(String[] args) {
		Customer customer = new Customer(new Profile(new GoldCard(5)));
		System.out.println(new OptionalVsNPE().getDiscountLine(customer));
	}
	
	public String getDiscountLine(Customer customer) {
		if (customer.getProfile() == null) {
			return "";
		}
		Integer discount = getApplicableDiscountPercent(customer.getProfile().getGoldCard());
		if (discount == null) {
			return "";
		}
		return "Discount: " + discount;
	}
	
	private Integer getApplicableDiscountPercent(GoldCard card) { 
		if (card.getFidelityPoints() >= 100) {
			return 5;
		}
		if (card.getFidelityPoints() >= 50) {
			return 3;
		}
		return null;
	}
}

