package victor.training.java8.stream.order.entity;

import java.time.LocalDate;

public class Audit {

	public enum Action {
		CREATE, MODIFY, DELETE, DEACTIVATE, ACTIVATE
	}

	private String user;
	private LocalDate date;
	private Action action;
	private String modifiedField;

	public LocalDate getDate() {
		return date;
	}

	public Audit setDate(LocalDate date) {
		this.date = date;
		return this;
	}

	public Action getAction() {
		return action;
	}

	public Audit setAction(Action action) {
		this.action = action;
		return this;
	}

	public String getModifiedField() {
		return modifiedField;
	}

	public void setModifiedField(String modifiedField) {
		this.modifiedField = modifiedField;
	}

	public String getUser() {
		return user;
	}

	public Audit setUser(String user) {
		this.user = user;
		return this;
	}
}
