package victor.training.eepatterns.singleton;


public class LegacyNonThreadSafeSMSClient {
	static private boolean guard;
	
	public void sendSMS() {
		if (guard) {
			throw new IllegalAccessError("SMS Client is non thread safe: it must be accessed by one thread at a time!");
		}
		guard = true;
		
		//do important stuff		
		try {
			Thread.sleep(100);
			System.out.println("Sent SMS");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		guard = false;
	}
}
