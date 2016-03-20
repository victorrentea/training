package victor.training.spring.spa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import victor.training.spring.spa.controller.dto.CourseDto;
import victor.training.spring.spa.dao.CourseRepository;
import victor.training.spring.spa.dao.TeacherRepository;
import victor.training.spring.spa.domain.Course;
import victor.training.spring.spa.service.CoursesService;

@RestController
@RequestMapping("/rest/courses")
public class CoursesController {
	
	private final static Logger log = LoggerFactory.getLogger(CoursesController.class);

	@Autowired
	private CoursesService service;
	
	@Autowired
	private CourseRepository courseRepo;
	
	@Autowired
	private TeacherRepository teacherRepo;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<CourseDto> getAllCourses() {
		List<CourseDto> dtos = new ArrayList<CourseDto>();
		for (Course course : courseRepo.getAll()) {
			dtos.add(map(course));
		}
		return dtos;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public CourseDto getCourseById(@PathVariable("id") Long id) {
		return mapWithDetails(courseRepo.getById(id));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void updateCourse(@PathVariable("id") Long id, @RequestBody CourseDto dto) throws ParseException {
		if (courseRepo.getByName(dto.name) != null &&  !courseRepo.getByName(dto.name).getId().equals(id)) {
			throw new IllegalArgumentException("Another course with that name already exists");
		}
		Course course = courseRepo.getById(id);
		course.setName(dto.name);
		course.setStartDate(new SimpleDateFormat("dd-MM-yyyy").parse(dto.startDate));
		course.setTeacher(teacherRepo.getById(dto.teacherId));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteCourseById(@PathVariable("id") Long id) {
		courseRepo.delete(id);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public void createCourse(@RequestBody CourseDto dto) throws ParseException {
		if (courseRepo.getByName(dto.name) != null) {
			throw new IllegalArgumentException("Another course with that name already exists");
		}
		courseRepo.save(map(dto));
	}

	private CourseDto map(Course course) {
		CourseDto dto = new CourseDto();
		dto.id = course.getId();
		dto.name = course.getName();
		dto.startDate = new SimpleDateFormat("dd-MM-yyyy").format(course.getStartDate());
		dto.teacherId = course.getTeacher().getId();
		dto.teacherName = course.getTeacher().getName();
		return dto ;
	}
	
	private CourseDto mapWithDetails(Course course) {
		CourseDto dto = map(course);
		dto.description = course.getDescription();
		return dto ;
	}
	
	private Course map(CourseDto dto) throws ParseException {
		Course newEntity = new Course();
		newEntity.setName(dto.name);
		newEntity.setStartDate(new SimpleDateFormat("dd-MM-yyyy").parse(dto.startDate));
		newEntity.setDescription(dto.description);
		newEntity.setTeacher(teacherRepo.getById(dto.teacherId));
		return newEntity;
	}
	
	
}
