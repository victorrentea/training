package ejb.container.tx;

import javax.ejb.Local;

import ejb.container.tx.model.Notification;

@Local
public interface NotificationDAO {
	void persist(Notification notification);
}