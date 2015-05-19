package victor.training.ejb.container.tx;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import victor.training.ejb.container.tx.model.Employee;
import victor.training.ejb.container.tx.model.Notification;

@Stateless
public class HRServiceBean implements HRService {

	private static final Logger log = Logger.getLogger(HRServiceBean.class);

	@EJB
	private EmployeeDAO employeeDao;

	@EJB
	private NotificationDAO notificationDao;

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Employee getEmployeeById(String employeeId) {
		return employeeDao.getById(employeeId);
	}

	@Override
	public void createEmployee(Employee newEmployee) {
		employeeDao.persist(newEmployee);
	}

	@Override
	public void removeEmployee(String employeeId) {
		employeeDao.removeById(employeeId);
	}

	@Override
	public void switchPhones(String employee1Id, String employee2Id) {
		try {
			Employee employee1 = employeeDao.getById(employee1Id);
			Employee employee2 = employeeDao.getById(employee2Id);

			String phone1 = employee1.getPhone();
			String phone2 = employee2.getPhone();
			employee1.setPhone(phone2);
			employee2.setPhone(phone1);

		} catch (IllegalArgumentException e) {
			log.error("Error switching phones: Persisting error notification", e);
			notificationDao.persist(new Notification("Employee id " + employee1Id + " not found"));
		}
	}

	public void setEmployeeDao(EmployeeDAO employeeDAO) {
		this.employeeDao = employeeDAO;
	}

	public void setNotificationDao(NotificationDAO notificationDao) {
		this.notificationDao = notificationDao;
	}

}
