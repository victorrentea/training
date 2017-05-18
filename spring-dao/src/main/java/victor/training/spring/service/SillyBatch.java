package victor.training.spring.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import victor.training.spring.dao.NotificationDAO;
import victor.training.spring.domain.Notification;

@Service
public class SillyBatch {
	
	private final static Logger log = LoggerFactory.getLogger(SillyBatch.class);

	@Autowired
	private EmployeeService service;
	
	@Autowired
	private NotificationDAO notificationDao;
	
	@Transactional
	public void updateManyPhones(List<String> employeeIds, List<String> newPhones) {
		
		try {
			for (int i = 0; i < employeeIds.size(); i++) {
				String employeeId = employeeIds.get(i);
				String newPhone = newPhones.get(i);
				service.updateEmployeePhone(employeeId, newPhone);
			}
		} catch (Exception e) {
			notificationDao.persist(new Notification("Crashed due to " + e.getMessage()));
			log.error("Batch failed", e);
			throw e;
		}
	}
}
