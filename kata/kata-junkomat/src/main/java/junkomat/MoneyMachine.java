package junkomat;

import java.util.Optional;

class MoneyMachine {
	private Coins coinStash = new Coins();
	
	public void empty() {
		coinStash = new Coins();
	}
	
	public void refill(Coins coins) {
		coinStash = coinStash.add(coins);
	}
	
	public Coins acceptPayment(int priceCents, Coins payedCoins) {
		Coins coinsToReturn = tryPayment(priceCents, payedCoins);
		coinStash = coinStash.add(payedCoins).substract(coinsToReturn);
		return coinsToReturn;
	}
	private Coins tryPayment(int priceCents, Coins payedCoins) {
		int payedCents = payedCoins.sumTotalCents();
		if (payedCents < priceCents) {
			throw new IllegalArgumentException("Not enough money provided");
		}
		int centsToReturn = payedCents - priceCents;
		return coinStash.add(payedCoins).computeChange(centsToReturn)
				.orElseThrow(() -> new IllegalArgumentException("Cannot provide rest"));
	}

	public int sumTotalCents() {
		return coinStash.sumTotalCents();
	}
	
}