package victor.training.java8;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CallbacksTest {
	private Subject subject = new Subject();

	private String receivedMessage="NONE";
	@Test
	public void attachAndFire() {
		subject.addEventHandler(event -> {
			System.out.println(event);
			receivedMessage=event;
		}
		);
		subject.fire();
		assertThat(receivedMessage, startsWith("Event now"));
	}
}
