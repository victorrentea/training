package ro.victor.training.jpa2.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.victor.training.jpa2.facade.TheFacade;
import ro.victor.training.jpa2.facade.dto.YearWithGroupsDto;
import ro.victor.training.jpa2.repo.StudentsYearRepo;

@RestController
@RequestMapping("/api/year")
public class YearController {
	
	@Autowired
	private StudentsYearRepo yearRepo;
	@Autowired
	private TheFacade facade;

	@GetMapping("{yearId}")
	public YearWithGroupsDto getYearWithGroups(@PathVariable long yearId) {
		return new YearWithGroupsDto(yearRepo.getExactlyOne(yearId));
	}
	
	@PutMapping("{yearId}")
	public void updateYearWithGroups(@PathVariable long yearId, @RequestBody YearWithGroupsDto dto) {
		facade.updateYearWithGroups(yearId, dto);
	}
}
