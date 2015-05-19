package victor.training.eepatterns.payloadextractor.mdb;

import java.util.HashMap;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jms.Message;
import javax.jms.MessageListener;

import victor.training.eepatterns.payloadextractor.PayloadExtractor;
import victor.training.eepatterns.payloadextractor.facade.AsyncBusinessFacade;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/map_in"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
@Interceptors(PayloadExtractor.class)
public class MapMessageMDBAfter implements MessageListener {

	@Inject
	private AsyncBusinessFacade facade;

	@Override
	public void onMessage(Message message) {
	}

	public void consume(HashMap<String, Object> map) {
		String param1 = (String) map.get("param1");
		String param2 = (String) map.get("param2");
		facade.consumeMapMessage(param1, param2);
	}

}
