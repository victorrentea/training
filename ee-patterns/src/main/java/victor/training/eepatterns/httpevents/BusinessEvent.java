package victor.training.eepatterns.httpevents;

public class BusinessEvent {
	private String text;
	
	

	public BusinessEvent() {
	}

	public BusinessEvent(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
