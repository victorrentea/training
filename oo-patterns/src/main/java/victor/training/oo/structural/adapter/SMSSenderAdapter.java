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
		// TODO transform parameters. invoke the delegate and then convert the result
		// return false; // INITIAL
		String phoneNumber = phone.toString(); // SOLUTION(
		int status = adaptee.sendSMS(phoneNumber, message); 
		return status == LegacySMSSender.SUCCESS; // SOLUTION)
	}
}
