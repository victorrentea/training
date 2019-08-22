package victor.training.jpa.app.web;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import victor.training.jpa.app.facade.TheFacade;
import victor.training.jpa.app.facade.dto.ContactChannelDto;
import victor.training.jpa.app.facade.dto.TeacherDetailsDto;
import victor.training.jpa.app.facade.dto.TeacherDto;
import victor.training.jpa.app.repo.TeacherRepo;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
	private static final Logger log = LoggerFactory.getLogger(TeacherController.class); 
	
	@Autowired
	private TeacherRepo teacherRepo;
	
	@Autowired
	private TheFacade facade;
	
	@GetMapping
	public List<TeacherDto> getAll() {
		return teacherRepo.findAll().stream().map(TeacherDto::new).collect(toList());
	}
	
	@PostMapping
	public Long create(@RequestBody TeacherDetailsDto dto) {
		return facade.createTeacher(dto);
	}
	
	@GetMapping("{teacherId}")
	public TeacherDetailsDto getById(@PathVariable Long teacherId) {
		return facade.getTeacher(teacherId);
	}
	
	@GetMapping("{teacherId}/channels")
	public List<ContactChannelDto> getTeacherContactChannels(@PathVariable long teacherId) {
		return facade.getTeacherChannels(teacherId);
	}
	@PutMapping("{teacherId}/channels")
	public void setTeacherContactChannels(@PathVariable long teacherId, @RequestBody List<ContactChannelDto> channelDtos) {
		facade.setTeacherChannels(teacherId, channelDtos);
	}
	
}
