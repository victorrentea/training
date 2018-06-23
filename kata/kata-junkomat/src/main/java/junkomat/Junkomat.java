package junkomat;

import java.util.HashMap;
import java.util.Map;

public class Junkomat {

	private Map<Code, ProductInfo> products = new HashMap<>();
	private MoneyMachine moneyMachine = new MoneyMachine();
	
	public void refillMoney(Coins coins) {
		moneyMachine.refill(coins);
	}
	
	public ProductAndRest buyProduct(Code selection, Coins payedCoins) {
		ProductInfo product = getProductInStock(selection);
		Coins restCoins = moneyMachine.acceptPayment(product.getPriceInCents(), payedCoins);
		product.decreaseStock();
		return new ProductAndRest(product.getName(), restCoins);
	}
	
	public int sumTotalCents() {
		return moneyMachine.sumTotalCents();
	}

	private ProductInfo getProductInStock(Code selection) {
		if (!products.containsKey(selection)) {
			throw new IllegalArgumentException("Invalid selection: " + selection);
		}
		ProductInfo product = products.get(selection);
		if (!product.isInStock()) {
			throw new IllegalArgumentException("Stock empty for selection: " + selection);
		}
		return product;
	}

	public void refillProducts(Map<Code, ProductInfo> products) {
		 this.products = products;
	}

	public int sumTotalProducts() {
		return products.values().stream().mapToInt(ProductInfo::getQuantity).sum();
	}
	
}
