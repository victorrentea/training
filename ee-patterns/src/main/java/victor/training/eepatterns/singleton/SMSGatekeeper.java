package victor.training.eepatterns.singleton;

import javax.ejb.Singleton;
import javax.inject.Inject;

@Singleton
public class SMSGatekeeper {

	@Inject
	private LegacyNonThreadSafeSMSClient smsClient;

	public void sendSMS() {
		smsClient.sendSMS();
	}
}
