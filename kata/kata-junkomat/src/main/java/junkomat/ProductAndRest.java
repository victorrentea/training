package junkomat;

class ProductAndRest {
	private final String productName;
	private final Coins returnedCoins;
	public ProductAndRest(String productName, Coins returnedCoins) {
		this.productName = productName;
		this.returnedCoins = returnedCoins;
	}
	public String getProductName() {
		return productName;
	}
	public Coins getReturnedCoins() {
		return returnedCoins;
	}
}