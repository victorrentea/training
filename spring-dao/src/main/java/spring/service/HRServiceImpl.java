package spring.service;

import org.apache.log4j.Logger;
import org.springframework.dao.DataRetrievalFailureException;

import spring.dao.EmployeeDAO;
import spring.dao.NotificationDAO;
import spring.model.Employee;
import spring.model.Notification;

public class HRServiceImpl implements HRService {

	private static final Logger log = Logger.getLogger(HRServiceImpl.class);

	private EmployeeDAO employeeDao;

	private NotificationDAO notificationDao;

	@Override
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

			employeeDao.update(employee1);
			employeeDao.update(employee2);
		} catch (DataRetrievalFailureException e) {
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
