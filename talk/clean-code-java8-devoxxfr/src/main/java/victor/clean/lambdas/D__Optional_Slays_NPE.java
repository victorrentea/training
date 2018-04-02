package victor.clean.lambdas;

import lombok.Data;

class DiscountService {
	
}








//-------- supporting (dummy) code ---------
class Customer {
	private Profile profile;
	public Customer(Profile profile) {
		this.profile = profile;
	}
	public Profile getProfile() {
		return profile;
	}
}

@Data
class Profile {
	private final GoldCard goldCard;
}

@Data
class GoldCard {
	private final int fidelityPoints;
}
