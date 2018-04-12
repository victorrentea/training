package victor.clean.lambdas;

import java.util.Random;
import java.util.function.Consumer;

import lombok.Data;

class EmailService {
	public void sendEmail(String emailAddress, Consumer<Email> fillEmail) {
		EmailContext context = new EmailContext(/*smtpConfig, etc*/);
		int MAX_RETRIES = 3;
		for (int i = 0; i < MAX_RETRIES; i++ ) {
			Email email = new Email(); // Generates new ID
			email.setSender("noreply@corp.com");
			email.setReplyTo("/dev/null");
			email.setTo(emailAddress);
			fillEmail.accept(email);
			boolean success = context.send(email);
			if (success) break;
			
		}
	}

	
	public static void main(String[] args) {
		EmailService emailService = new EmailService();
		emailService.sendEmail("a@b.com", EmailFillers::fillOrderReceivedEmail);
		emailService.sendEmail("a@b.com", EmailFillers::fillOrderShippedEmail);
	}
}
class EmailFillers {
	public static void fillOrderReceivedEmail(Email email) {
		email.setSubject("Order Received");
		email.setBody("Thank you for your order!");
	}
	public static void fillOrderShippedEmail(Email email) {
		email.setSubject("Order Shipped");
		email.setBody("We've sent you. Good luck!");
	}
}

// VVVVVVVVV ==== supporting (dummy) code ==== VVVVVVVVV
class EmailContext implements AutoCloseable {
	public boolean send(Email email) {
		boolean r = new Random().nextBoolean();
		System.out.println("Sending "+(r?"OK":"KO")+" email " + email.getSubject());
		return r;
	}

	public void close() {
	}
}

@Data
class Email {
	private String sender;
	private String subject;
	private String body;
	private String replyTo;
	private String to;
}
