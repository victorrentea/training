package ro.victor.training.jpa2.facade.dto;

import java.util.ArrayList;
import java.util.List;

import ro.victor.training.jpa2.domain.entity.Subject;
import ro.victor.training.jpa2.domain.entity.TeachingActivity;

public class SubjectWithActivitiesDto {

	public Long id;
	public String name;
	public Long holderTeacherId;
	public List<ActivityDto> activities = new ArrayList<>();
	
	public SubjectWithActivitiesDto() {
	}
	
	public SubjectWithActivitiesDto(Subject subject) {
		id = subject.getId();
		name = subject.getName();
		if (subject.getHolderTeacher() != null) {
			holderTeacherId = subject.getHolderTeacher().getId();
		}
		for (TeachingActivity activity : subject.getActivities()) {
			activities.add(new ActivityDto(activity));
		}
	}
}
