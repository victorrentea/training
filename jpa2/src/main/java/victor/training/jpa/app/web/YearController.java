package victor.training.jpa.app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import victor.training.jpa.app.facade.TheFacade;
import victor.training.jpa.app.facade.dto.YearWithGroupsDto;
import victor.training.jpa.app.repo.StudentsYearRepo;

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
		facade.updateYearWithGroups_cascadingMerge(yearId, dto);
	}
}
