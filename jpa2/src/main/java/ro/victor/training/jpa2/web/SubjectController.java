package ro.victor.training.jpa2.web;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

import ro.victor.training.jpa2.facade.TheFacade;
import ro.victor.training.jpa2.facade.dto.SubjectDto;
import ro.victor.training.jpa2.facade.dto.SubjectWithActivitiesDto;
import ro.victor.training.jpa2.repo.SubjectRepo;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
	private static final Logger log = LoggerFactory.getLogger(SubjectController.class); 
	
	@Autowired
	private TheFacade facade;

	@Autowired
	private SubjectRepo subjectRepo;
	
	@PersistenceContext
	private EntityManager em;
	
	@GetMapping
	public List<SubjectDto> getAll() {
		return subjectRepo.findAll().stream().map(SubjectDto::new).collect(toList());
	}
	
	@GetMapping("{subjectId}")
	public SubjectWithActivitiesDto getById(@PathVariable Long subjectId) {
		return facade.getSubjectWithActivities(subjectId);
	}
	
	@PostMapping
	public Long create(@RequestBody SubjectDto subjectDto) {
		return facade.createSubject(subjectDto);
	}
	
	@PutMapping
	public void update(@RequestBody SubjectDto subjectDto) {
		facade.updateSubject(subjectDto);
	}
	
	@PutMapping("/ko")
	public void updateSubjectFailing(@RequestBody SubjectDto subjectDto) {
		facade.updateSubjectFailing(subjectDto);
	}
	@PutMapping("/ko-logged")
	public void updateSubjectFailingLogged(@RequestBody SubjectDto subjectDto) {
		facade.updateSubjectFailingLogged(subjectDto);
	}
	
}
