package victor.training.ejb.container.tx;

import javax.ejb.Local;

import victor.training.ejb.container.tx.model.Notification;

@Local
public interface NotificationDAO {
	void persist(Notification notification);
}