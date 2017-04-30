package victor.training.jpa.facade;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import victor.training.jpa.entity.Employee;
import victor.training.jpa.entity.Project;

@Service
public class EmployeeService {

	@PersistenceContext
	private EntityManager em;
	
	public void removeFromProject(int employeeId, int projectId) {
		Employee employee = em.find(Employee.class, employeeId);
		Project project = em.find(Project.class, projectId);
		System.out.println("Project from UserService: " + project);
		project.removeEmployee(employee);
	}

}
