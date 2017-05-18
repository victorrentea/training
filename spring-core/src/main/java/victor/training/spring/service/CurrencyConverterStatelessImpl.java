package victor.training.spring.service;

import javax.annotation.PostConstruct;

public class CurrencyConverterStatelessImpl implements CurrencyConverter {
	
	@PostConstruct
	public void annotationBasedInitialization() {
		System.out.println("Currency Converter - StatelessImpl initialized");
	}

	@Override
	public Double convert(Double amount, String currencyFrom, String currencyTo) {
		throw new UnsupportedOperationException();
	}

}
