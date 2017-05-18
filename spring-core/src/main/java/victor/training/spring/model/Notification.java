package victor.training.spring.model;

import java.util.UUID;

public class Notification {

	private String id = UUID.randomUUID().toString();
	private String message;

	public Notification() {
	}

	public Notification(String message) {
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
