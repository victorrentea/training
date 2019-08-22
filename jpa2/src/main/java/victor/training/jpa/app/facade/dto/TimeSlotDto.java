package victor.training.jpa.app.facade.dto;

import java.time.DayOfWeek;

public class TimeSlotDto {
	public DayOfWeek day;
	public int startHour;
	public int durationInHours;
	public String roomId;
	
	public TimeSlotDto() {
	}
	public TimeSlotDto(DayOfWeek day, int startHour, int durationInHours, String roomId) {
		this.day = day;
		this.startHour = startHour;
		this.durationInHours = durationInHours;
		this.roomId = roomId;
	}
	
}
