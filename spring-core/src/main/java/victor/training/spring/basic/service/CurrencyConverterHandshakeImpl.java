package victor.training.spring.basic.service;

public class CurrencyConverterHandshakeImpl implements CurrencyConverter {
	
	public void initialize() {
		/* Time-consuming initialization that must be called before first usage */
		System.out.println("Currency Converter - HandshakeImpl initialized");
	}

	@Override
	public Double convert(Double amount, String currencyFrom, String currencyTo) {
		throw new UnsupportedOperationException();
	}

}
