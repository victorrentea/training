package victor.clean.lambdas.opt;

class Customer {
	private Profile profile;
	public Customer(Profile profile) {
		this.profile = profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
//	public Optional<Profile> getProfileOpt() {
//		return ofNullable(profile);
//	}
	public Profile getProfile() {
		return profile;
	}
}

class Profile {
	private GoldCard goldCard;
	public Profile(GoldCard goldCard) {
		this.goldCard = goldCard;
	}

	public GoldCard getGoldCard() {
		return goldCard;
	}
	public void setGoldCard(GoldCard goldCard) {
		this.goldCard = goldCard;
	}
}

class GoldCard {
	private int fidelityPoints;
	public GoldCard(int fidelityPoints) {
		this.fidelityPoints = fidelityPoints;
	}
	public int getFidelityPoints() {
		return fidelityPoints;
	}
	public void setFidelityPoints(int fidelityPoints) {
		this.fidelityPoints = fidelityPoints;
	}
}
