package victor.training.spring.basic.service;

public interface CurrencyConverter {
	public Double convert(Double amount, String currencyFrom, String currencyTo);
}
