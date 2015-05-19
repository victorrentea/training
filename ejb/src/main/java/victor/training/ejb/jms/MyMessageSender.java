package victor.training.ejb.jms;

import javax.ejb.Local;

@Local
public interface MyMessageSender {
	void sendMessage(String payload);
}
