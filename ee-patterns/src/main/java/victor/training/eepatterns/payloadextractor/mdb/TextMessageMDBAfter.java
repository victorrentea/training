package victor.training.eepatterns.payloadextractor.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jms.Message;
import javax.jms.MessageListener;

import victor.training.eepatterns.payloadextractor.PayloadExtractor;
import victor.training.eepatterns.payloadextractor.facade.AsyncBusinessFacade;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/text_in"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
@Interceptors(PayloadExtractor.class)
public class TextMessageMDBAfter implements MessageListener {

	@Inject
	private AsyncBusinessFacade facade;

	@Override
	public void onMessage(Message message) {
	}

	public void consume(String payload) {
		System.out.println("in Consume method");
		facade.consumeTextMessage(payload);
	}

}
