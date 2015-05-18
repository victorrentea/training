package ejb.container.tx;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;

import ejb.container.tx.model.Employee;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class EmployeeDAOBean implements EmployeeDAO {

	// Hack ONLY FOR TEST
	public static EntityManager em;

	@Override
	public void persist(Employee employee) {
		em.persist(employee);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Employee getById(String id) {
		return em.find(Employee.class, id);
	}

	@Override
	public void removeById(String employeeId) {
		em.createQuery("DELETE FROM Employee e WHERE e.id = :id").setParameter("id", employeeId).executeUpdate();
	}
	
}
