package victor.training.oo.structural.adapter.external;

/**
 * External system to adapt.
 */
public class LegacySMSSender {
	public static final int SUCCESS = 0;
	public static final int FAILURE = -1;
	public static final int INTERNAL_ERROR = -2;

	public int sendSMS(String phoneNumber, String message) {
		System.out.println("Sending SMS to phone : " + phoneNumber);
		// actually send SMS to phone number
		return SUCCESS;
	}
}
