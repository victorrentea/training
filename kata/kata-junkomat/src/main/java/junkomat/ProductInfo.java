package junkomat;

class ProductInfo {
	private final String name;
	private final int priceInCents;
	private int quantity;
	
	public ProductInfo(String name, int priceInCents, int quantity) {
		this.name = name;
		this.priceInCents = priceInCents;
		this.quantity = quantity;
	}
	public String getName() {
		return name;
	}
	public int getPriceInCents() {
		return priceInCents;
	}
	public boolean isInStock() {
		return quantity >= 1;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void decreaseStock() {
		if (quantity == 0) {
			throw new IllegalStateException();
		}
		quantity --;
	}
	public void refill(int newQuantity) {
		quantity = newQuantity;
	}
	
	public String toString() {
		return "ProductInfo [name=" + name + ", priceInCents=" + priceInCents + ", quantity=" + quantity + "]";
	}

}