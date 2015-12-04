package spring.dao;

import javax.persistence.EntityManager;

import spring.model.Notification;

public interface NotificationDAO {
	void persist(Notification notification);

	long getCount();

	void setEm(EntityManager em);
}
