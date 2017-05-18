package victor.training.spring.service;

public interface CurrencyConverter {
	public Double convert(Double amount, String currencyFrom, String currencyTo);
}
