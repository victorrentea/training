package junkomat;

enum Coin {
	TEN_CENTS(10),
	TWENTY_CENTS(20),
	FIFTY_CENTS(50),
	ONE_EURO(100),
	TWO_EUROS(200);
	
	public final int cents;

	private Coin(int value) {
		this.cents = value;
	}
	
	public int getCents() {
		return cents;
	}
	
}