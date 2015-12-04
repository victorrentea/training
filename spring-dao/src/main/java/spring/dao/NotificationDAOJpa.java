package spring.dao;

import javax.persistence.EntityManager;

import spring.model.Notification;

public class NotificationDAOJpa implements NotificationDAO {

	private EntityManager em;

	@Override
	public void persist(Notification notification) {
		em.persist(notification);
	}

	@Override
	public long getCount() {
		return (long)(Long) em.createQuery("SELECT COUNT(n) FROM Notification n").getSingleResult();
	}
	
	@Override
	public void setEm(EntityManager em) {
		this.em = em;
	}
}
