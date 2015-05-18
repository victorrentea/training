package victor.training.oo.structural.adapter;

import victor.training.oo.structural.adapter.external.LegacySMSSender;

/**
 * Concrete Adapter
 */
public class SMSSenderAdapter implements SMSSender {
	private final LegacySMSSender adaptee;

	public SMSSenderAdapter(LegacySMSSender adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public boolean sendSMS(Phone phone, String message) {
		String phoneNumber = phone.toString();
		int status = adaptee.sendSMS(phoneNumber, message);
		return status == LegacySMSSender.SUCCESS;
	}
}
