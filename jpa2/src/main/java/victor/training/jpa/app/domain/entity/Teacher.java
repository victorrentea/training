package victor.training.jpa.app.domain.entity;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

@Entity
public class Teacher {

	public enum Grade {
		LECTURER, PROFESSOR, CONF, ASSISTENT
	}
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Grade grade;
	
	@OneToOne(cascade = CascadeType.ALL)
	private TeacherDetails details;
	
	@ElementCollection
//	@OrderColumn(name="INDEX")
	@OrderBy("type ASC, value ASC")
	private List<ContactChannel> channels = new ArrayList<>();

	@OneToMany(mappedBy = "holderTeacher")
	private Set<Subject> heldSubjects = new HashSet<>() ;
	
	@ManyToMany(mappedBy = "teachers")
	private Set<TeachingActivity> activities = new HashSet<>();
	
	@Enumerated(EnumType.STRING)
	private DayOfWeek counselingDay;
	
	private int counselingStartHour;
	
	private int counselingDurationInHours;
	
	private String counselingRoomId;
	
	public Teacher() {
	}
	
	public Teacher(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TeacherDetails getDetails() {
		return details;
	}

	public void setDetails(TeacherDetails details) {
		this.details = details;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public Set<TeachingActivity> getActivities() {
		return activities;
	}

	public void setActivities(Set<TeachingActivity> activities) {
		this.activities = activities;
	}

	public DayOfWeek getCounselingDay() {
		return counselingDay;
	}

	public void setCounselingDay(DayOfWeek counselingDay) {
		this.counselingDay = counselingDay;
	}

	public int getCounselingStartHour() {
		return counselingStartHour;
	}

	public void setCounselingStartHour(int counselingStartHour) {
		this.counselingStartHour = counselingStartHour;
	}

	public int getCounselingDurationInHours() {
		return counselingDurationInHours;
	}

	public void setCounselingDurationInHours(int counselingDurationInHours) {
		this.counselingDurationInHours = counselingDurationInHours;
	}

	public String getCounselingRoomId() {
		return counselingRoomId;
	}

	public void setCounselingRoomId(String counselingRoomId) {
		this.counselingRoomId = counselingRoomId;
	}

	public List<ContactChannel> getChannels() {
		return channels;
	}

	public void setChannels(List<ContactChannel> channel) {
		this.channels = channel;
	}

	public Set<Subject> getHeldSubjects() {
		return heldSubjects;
	}

	public void setHeldSubjects(Set<Subject> heldSubjects) {
		this.heldSubjects = heldSubjects;
	}
	
	
	
	

	
	

}
