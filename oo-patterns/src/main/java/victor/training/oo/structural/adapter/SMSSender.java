package victor.training.oo.structural.adapter;

/**
 * The Adapter interface
 */
public interface SMSSender {
	boolean sendSMS(Phone phone, String message);
}	
