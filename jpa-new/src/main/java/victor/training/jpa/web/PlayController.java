package victor.training.jpa.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import victor.training.jpa.entity.search.EmployeeSearchCriteria;
import victor.training.jpa.facade.PlayFacade;
import victor.training.jpa.facade.dto.CompanyDto;
import victor.training.jpa.facade.dto.EmployeeDto;
import victor.training.jpa.facade.dto.ProjectDto;
import victor.training.jpa.facade.dto.SiteDto;
import victor.training.jpa.repository.NativeRepository;

@RestController
@RequestMapping("/rest")
public class PlayController {

	@Autowired
	private PlayFacade facade;
	
	@RequestMapping(value = "/companies", method = RequestMethod.GET)
	public List<CompanyDto> getAllCompanies() {
		return facade.getAllCompanies();
	}
	
	@RequestMapping(value = "/sites", method = RequestMethod.GET)
	public List<SiteDto> getAllSites() {
		return facade.getAllSites();
	}
	
	@RequestMapping(value = "/projects/{projectId}", method = RequestMethod.GET)
	public ProjectDto getFullProject(@PathVariable("projectId") int projectId) {
		return facade.getProjectWithEmployees(projectId);
	}
	
	@RequestMapping(value = "/employees/search", method = RequestMethod.POST)
	public List<EmployeeDto> searchEmployees(@RequestBody EmployeeSearchCriteria criteria) {
		return facade.searchEmployees(criteria);
	}
	
	@RequestMapping(value = "/sites", method = RequestMethod.POST)
	public void createSite(@RequestBody SiteDto siteDto) {
		facade.createSite(siteDto);
	}
	
	@RequestMapping(value = "/sites/{siteId}/name", method = RequestMethod.PUT)
	public void updateSiteName(@PathVariable("siteId") int siteId, @RequestBody String newName) {
		facade.updateSiteName(siteId, newName);
	}

	@RequestMapping(value = "/employees", method = RequestMethod.POST)
	public int createEmployee(@RequestBody EmployeeDto employeeDto) {
		return facade.createEmployee(employeeDto);
	}
	
	@RequestMapping(value = "/employees/{employeeId}/assignToProject/{projectId}")
	public void assignEmployeeToProject(
			@PathVariable("employeeId") int employeeId,
			@PathVariable("projectId") int projectId) {
		facade.assignEmployeeToProject(employeeId, projectId);
	}
	
	@RequestMapping(value = "/employees/{employeeId}/removeFromProject/{projectId}")
	public void removeEmployeeFromProject(
			@PathVariable("employeeId") int employeeId,
			@PathVariable("projectId") int projectId) {
		facade.removeEmployeeFromProject(employeeId, projectId);
	}
	
	@RequestMapping(value = "/projects/export.csv", method = RequestMethod.GET)
//	public String exportProjects() throws IOException { // INITIAL
	public void exportProjects(HttpServletResponse response) throws IOException { // SOLUTION
		facade.exportAllProjects(response.getWriter()); //SOLUTION
		// return facade.exportAllProjects(); // INITIAL
	}
	
	@Autowired
	private NativeRepository debugRepo; // never inject Repos in Controllers!
	@RequestMapping(value = "/db", produces="text/html")
	public String printDbToString() {
		return debugRepo.allDatabaseToString();
	}
	
}
