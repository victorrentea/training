package ro.victor.training.jpa2.web;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ro.victor.training.jpa2.facade.TheFacade;
import ro.victor.training.jpa2.facade.dto.LabDto;
import ro.victor.training.jpa2.facade.dto.TimeSlotDto;
import ro.victor.training.jpa2.repo.LabRepo;

@RestController
@RequestMapping("/api/labs")
public class LabController {

	@Autowired
	private LabRepo labRepo; 
	
	@Autowired
	private TheFacade facade; 
	
	@GetMapping
	public List<LabDto> getAll() {
		return labRepo.findAll().stream().map(LabDto::new).collect(toList());
	}
	
	@PutMapping("{labId}/teacher")
	public void assignLabTeacher(@PathVariable long labId, @RequestBody Long teacherId) {
		facade.assignTeacherToLab(teacherId, labId);
	}
	
	@DeleteMapping("{labId}/teacher")
	public void removeTeacherFromLab(@PathVariable long labId, @RequestBody Long teacherId) {
		facade.removeTeacherFromLab(teacherId, labId);
	}
	
	@PostMapping
	public long addLab(@RequestParam long subjectId, @RequestBody TimeSlotDto timeSlotDto) {
		return facade.addLab(subjectId, timeSlotDto);
	}
	
	@DeleteMapping("{labId}") 
	public void deleteLab(@PathVariable long labId) {
		facade.deleteLab(labId);
	}
}
