package victor.training.jpa.facade;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import victor.training.jpa.entity.Company;
import victor.training.jpa.entity.Employee;
import victor.training.jpa.entity.EmployeeDetails;
import victor.training.jpa.entity.Project;
import victor.training.jpa.entity.Site;
import victor.training.jpa.entity.search.EmployeeSearchCriteria;
import victor.training.jpa.facade.dto.CompanyDto;
import victor.training.jpa.facade.dto.EmployeeDto;
import victor.training.jpa.facade.dto.ProjectDto;
import victor.training.jpa.facade.dto.SiteDto;

@Service
@Transactional
public class PlayFacade {
 
	@PersistenceContext
	private EntityManager em;
	
	// Takeaways:
	// - how to do a search query with JPQL
	public List<EmployeeDto> searchEmployees(EmployeeSearchCriteria criteria) {
		String jpql = "SELECT e FROM Employee e WHERE 1=1 ";
		Map<String, Object> params = new HashMap<>();
		// SOLUTION(
		if (criteria.name != null) {
			jpql += " AND UPPER(e.name) LIKE '%' || UPPER(:name) || '%' ";
			params.put("name", criteria.name);
		}
		if (criteria.siteId != null) {
			jpql += " AND e.site.id = :siteId ";
			params.put("siteId", criteria.siteId);
		}
		TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		List<Employee> employees = query.getResultList();
		// SOLUTION)
		// List<Employee> employees = new ArrayList<>();		// INITIAL
		return employees.stream()
				.map(EmployeeDto::new)
				.collect(toList());
	}
	
	// Takeaways: 
	// - reference an existing Entity in DB;
	// - getReference - no DB hit: assumes you know the ID
	public void createSite(SiteDto dto) {
		checkUniqueSiteName(dto.name);
		// SOLUTION(
		Site site = new Site();
		site.setName(dto.name);
		site.setCompany(em.getReference(Company.class, dto.companyId));  
		em.persist(site);
		// SOLUTION)
	}
	
	// Takeaway: JPQL :namedParams .getSingleResult()
	private void checkUniqueSiteName(String siteName) {
		// SOLUTION(
		TypedQuery<Long> query = em.createQuery("SELECT COUNT(s) FROM Site s WHERE UPPER(s.name) = UPPER(:newName)", Long.class);
		query.setParameter("newName", siteName);
		if (query.getSingleResult() != 0) {
			throw new IllegalArgumentException("The Site name already exists");
		}
		// SOLUTION)
	}
	
	// Takeaways: 
	// - autoflush of changes to entity
	// - ... as int as they are attached. (know your Tx boundaries) TODO move to Controller
	public void updateSiteName(int siteId, String newName) {
		// SOLUTION(
		checkUniqueSiteName(newName);
		Site site = em.find(Site.class, siteId);
		site.setName(newName);
		// SOLUTION)
	}
	
	// Takeaways: 
	// - "Detached entity passed to persist" - un .persit()ed entities linked
	// - set owner side
	// - enhance JPA Entity Model to ensure owner side is set
	// - cascade
	// - the generated ID is assigned to an entity during the .persist() call 
	public int createEmployee(EmployeeDto dto) {
		Employee employee = new Employee();
		employee.setName(dto.name);
		employee.setSite(em.getReference(Site.class, dto.siteId)); 
		
		// SOLUTION(
		EmployeeDetails details = new EmployeeDetails();
		details.setStartDate(dto.startDate);
		
		employee.setDetails(details);
		
		em.persist(details); // SOLUTION) 
		em.persist(employee);
		return employee.getId();
	}
	
	// Takeaway: 
	// - Many-To-Many links are automatically handled by JPA
	// - Any thrown RuntimeException invalidates the current Transcation --> ROLLBACK
	//    TODO throw exception if the employee gets assigned to 3 projects in parallel 
	public void assignEmployeeToProject(int employeeId, int projectId) {
		// SOLUTION(
		Employee employee = em.find(Employee.class, employeeId);
		Project project = em.find(Project.class, projectId);
		
		project.addEmployee(employee); 
		if (employee.getProjects().size() >= 3) {
			throw new RuntimeException("Not-A-Human!");
		}
		// SOLUTION)
	}
	
	
	public List<CompanyDto> getAllCompanies() {
		List<Company> companies = em.createQuery("SELECT c FROM Company c", Company.class)
				.getResultList();
		
		List<CompanyDto> dtos = new ArrayList<>();
		for (Company company : companies) {
			CompanyDto dto = new CompanyDto();
			dto.id = company.getId();
			dto.name = company.getName();
			dtos.add(dto);
		}
		return dtos;
	} 
	
	// ======== HARD STUFF ==========
	
	
	// Takeaway: campurile primitive si @...ToOne sunt incarcate automat, cu toate campurile (logs)
	public List<SiteDto> getAllSites() {
		// SOLUTION(
		List<Site> companies = em.createQuery("SELECT s FROM Site s", Site.class).getResultList(); 
		
		List<SiteDto> dtos = new ArrayList<>();
		for (Site site : companies) {
			SiteDto dto = new SiteDto();
			dto.id = site.getId();
			dto.name = site.getName();
			dto.companyId = site.getCompany().getId(); //was already brought from DB by JPA (check logs)
			dto.companyName = site.getCompany().getName(); 
			dtos.add(dto);
		}
		return dtos;
		// SOLUTION)
		//return null; // INITIAL
	}
	
	// Takeaways: 
	// - colectiile de copii sunt accesibile- Lazy Load (Versailles Syndrome) -check Logs
	// - if parent is not attached, "... no session" exception
	// - lookout for Transaction boundaries
	public ProjectDto getProjectWithEmployees(int projectId) {
		ProjectDto dto = new ProjectDto();
		// SOLUTION(
		Project project = em.find(Project.class, projectId);
		dto.id = project.getId();
		dto.name = project.getName();
		for (Employee e : project.getEmployees()) { // SWEEP OVER CHILDREN AS IF ALL IN MEMORY
			dto.employeeIds.add(e.getId());  // LAZY LOAD!! (check console)
		}
		// SOLUTION)
		return dto;
	}
	
	@Autowired
	private EmployeeService myService;
	
	// Takeaways:
	// - EntityManager = 1st Level Cache of the ORM
	// - EntityManager propagates with the Transaction
	public void removeEmployeeFromProject(int employeeId, int projectId) {
		Project project = em.find(Project.class, projectId);
		System.out.println("Initial project employee count: " + project.getEmployees().size());
		myService.removeFromProject(employeeId, projectId);
		System.out.println("Final project employee count: " + project.getEmployees().size());
		System.out.println("Project from Facade: " + project);
	}
	
	// Takeaways: 
	// - N+1 Queries (1 SELECT CHILDREN query for each parent in the list)
//	public String exportAllProjects() { // INITIAL
//		return "ProjName;EmployeeName1;EmployeeName2;...";// INITIAL
	// SOLUTION(
	public void exportAllProjects(Writer writer) throws IOException {
		List<Project> allProjects = em.createQuery("SELECT p FROM Project p LEFT JOIN FETCH p.employees", Project.class)
			.getResultList();
		
		for (Project project : allProjects) {
			String line = project.getName();
			for (Employee employee : project.getEmployees()) {
				line += ";" + employee.getName();
			}
			writer.write(line + "\n");
		}
		// SOLUTION)
	}

	// TODO lazy load on @...ToOne
}
