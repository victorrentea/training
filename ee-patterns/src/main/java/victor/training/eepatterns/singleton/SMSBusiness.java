package victor.training.eepatterns.singleton;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class SMSBusiness {

	 @Inject
	 private SMSGatekeeper gatekeeper;

	// @Inject
	// private LegacyNonThreadSafeSMSClient smsClient;

	public void sendSMS() {
		 gatekeeper.sendSMS();
//		smsClient.sendSMS();
	}
}
