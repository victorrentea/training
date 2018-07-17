package victor.training.java8.stream.order.dto;

import java.time.LocalDate;

import victor.training.java8.stream.order.entity.Audit.Action;

public class AuditDto {
	public String username;
	public LocalDate date;
	public Action action;

	public final LocalDate getDate() {
		return date;
	}
	public final Action getAction() {
		return action;
	}
	public final String getUsername() {
		return username;
	}
	
}
