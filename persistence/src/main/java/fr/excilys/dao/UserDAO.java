package fr.excilys.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import fr.excilys.model.UserRole;
import fr.excilys.model.Users;

@Repository
public class UserDAO {

	SessionFactory sessionFactory;

	public UserDAO(SessionFactory sessionFactory) {

		this.sessionFactory = sessionFactory;

	}

	public List<Users> getUser(String username) {

		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		String hqlCommand = EnumHQLCommand.ADD_USER.getMessage();
		TypedQuery<Users> query = (TypedQuery<Users>) session.createQuery(hqlCommand).setParameter("username",username);	
		return query.getResultList();
	}

	public void createUser(Users user) {

		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}
		session.save(user);
	}
	
	public void createUserRole(UserRole userRole) {
		
		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}
		session.save(userRole);
	}

	public List<UserRole> getUserRole(String username) {

		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		String hqlCommand = EnumHQLCommand.GET_USER_ROLE.getMessage();
		TypedQuery<UserRole> query = (TypedQuery<UserRole>) session.createQuery(hqlCommand).setParameter("username", username);
		return query.getResultList();

	}

	// TODO a virer avant CR

	public List<Users> getAllUser() {
		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		TypedQuery<Users> query = session.createQuery("FROM Users");
		return query.getResultList();
	}

}
