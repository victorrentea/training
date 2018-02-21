package victor.training.oo.behavioral.template;

import java.util.Random;

class EmailService {

	public void sendOrderReceivedEmail(String emailAddress) {
		EmailContext context = new EmailContext(/*smtpConfig,etc*/);
		int MAX_RETRIES = 3;
		for (int i = 0; i < MAX_RETRIES; i++ ) {
			Email email = new Email(); // constructor generates new unique ID
			email.setSender("noreply@corp.com");
			email.setReplyTo("/dev/null");
			email.setTo(emailAddress);
			email.setSubject("Order Received");
			email.setBody("Thank you for your order");
			boolean success = context.send(email);
			if (success) break;
		}
	}
}

class EmailContext {
	public boolean send(Email email) {
		System.out.println("Trying to send " + email);
		return new Random(System.nanoTime()).nextBoolean();
	}
}

class Email {
	private final long id = new Random(System.nanoTime()).nextLong();
	private String sender;
	private String subject;
	private String body;
	private String replyTo;
	private String to;
	public final String getSender() {
		return sender;
	}
	public final void setSender(String sender) {
		this.sender = sender;
	}
	public final String getSubject() {
		return subject;
	}
	public final void setSubject(String subject) {
		this.subject = subject;
	}
	public final String getBody() {
		return body;
	}
	public final void setBody(String body) {
		this.body = body;
	}
	public final String getReplyTo() {
		return replyTo;
	}
	public final void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}
	public final String getTo() {
		return to;
	}
	public final void setTo(String to) {
		this.to = to;
	}
	public final long getId() {
		return id;
	}
	@Override
	public String toString() {
		return "Email{subject="+subject+" to " + to +"}";
	}
}
