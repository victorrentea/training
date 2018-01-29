package ro.victor.training.jpa2.facade.dto;

import ro.victor.training.jpa2.domain.entity.TeachingActivity;

public class ActivityDto {
	public Long id;
	public String type;
	public TimeSlotDto timeSlot;
	
	public ActivityDto(TeachingActivity activity) {
		id = activity.getId();
		type = activity.getClass().getSimpleName();
		timeSlot =  new TimeSlotDto(activity.getDay(), activity.getDurationInHours(), activity.getDurationInHours(), activity.getRoomId());
	}
}
