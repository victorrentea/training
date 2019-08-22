package victor.training.jpa.app.web;

import static java.util.stream.Collectors.toList;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import victor.training.jpa.app.facade.dto.ActivityDto;
import victor.training.jpa.app.facade.dto.ActivitySearchCriteria;
import victor.training.jpa.app.facade.dto.SubjectDto;
import victor.training.jpa.app.facade.dto.TeacherDto;
import victor.training.jpa.app.repo.TeacherRepo;

@RestController
@RequestMapping("/api")
public class QueryController {
	private static final Logger log = LoggerFactory.getLogger(QueryController.class); 
	
	@Autowired
	private TeacherRepo teacherRepo;
	
	@PersistenceContext
	private EntityManager em;
	
	@GetMapping("/rooms/{roomId}/subjects")
	public List<SubjectDto> getById(@PathVariable String roomId) {
		return teacherRepo.getSubjectsInRoom(roomId).stream().map(SubjectDto::new).collect(toList());
	}
	
	@GetMapping("/teachers/{teacherId}/busy-days")
	public Set<DayOfWeek> getBusyDaysOfTeacher(@PathVariable long teacherId) {
		return teacherRepo.getBusyDaysOfTeacher(teacherId);
	}
	
	@GetMapping("/teachers/{teacherId}/subjects")
	public List<SubjectDto> getSubjectsKnownByTeacher(@PathVariable long teacherId) {
		return teacherRepo.getSubjectsKnownByTeacher(teacherId).stream().map(SubjectDto::new).collect(toList());
	}

	@GetMapping("/groups/{groupId}/teachers")
	public List<TeacherDto> getTeachersKnownByGroup(@PathVariable long groupId) {
		return teacherRepo.getTeachersKnownByGroup(groupId).stream().map(TeacherDto::new).collect(toList());
	}
	
	@GetMapping("/years/{yearId}/teachers")	
	public List<TeacherDto> getAllTeachersForYear(@PathVariable long yearId) {
		return teacherRepo.getAllTeachersForYear(yearId).stream().map(TeacherDto::new).collect(toList());
	}
	
	@PostMapping("/activities/search")	
	public List<ActivityDto> searchActivities(@RequestBody ActivitySearchCriteria searchCriteria) {
		return teacherRepo.searchActivity(searchCriteria).stream().map(ActivityDto::new).collect(toList());
	}
	
}
