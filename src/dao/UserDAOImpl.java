package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import model.Users;
import utilities.DAOUtilities;

public class UserDAOImpl implements UserDAO {
	Session session = null;
	Transaction tx = null;

	@SuppressWarnings("unchecked")
	@Override
	public List<Users> getAllUsers() {
		List<Users> users = new ArrayList<>();

		// Create Session

		session = DAOUtilities.getSession().openSession();

		try {
			tx = session.beginTransaction();
			users = session.createQuery("FROM Users").getResultList();
			// commit transaction
			session.getTransaction().commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();

		}

		return users;
	}

	/*------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("rawtypes")
	@Override
	public Users getUserByEmail(String email) {

		Users user = new Users();

		// call a session
		session = DAOUtilities.getSession().openSession();

		try {
			tx = session.beginTransaction();

			Query query = session.createQuery("FROM Users u where u.email= :email");
			query.setParameter("email", email);
			// get Unique result using result type that matches the query
			user = (Users) query.uniqueResult();

			// commit transaction
			session.getTransaction().commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();

		}

		return user;
	}

	/*------------------------------------------------------------------------------------------------*/
	@Override
	public Users getUserByusername(String username) {
		Users user = new Users();

		// call a session
		session = DAOUtilities.getSession().openSession();

		try {
			tx = session.beginTransaction();
			user = session.get(Users.class, username);
			// commit transaction
			session.getTransaction().commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();

		}

		return user;
	}

	/*------------------------------------------------------------------------------------------------*/
	
	@Override
	public boolean updateUserEmail(String username, String newEmail) {

		// call session
		session = DAOUtilities.getSession().openSession();

		try {
			tx = session.beginTransaction();

			Users user = getUserByusername(username);

			// update using session.update method
			user.setEmail(newEmail);
			session.update(user);

			// alternate method using HQL
			// String oldEmail = user.getEmail();
			//
			// @SuppressWarnings("rawtypes")
			// Query query = session.createQuery("UPDATE Users SET email=
			// :newEmail where email= :oldEmail");
			// query.setParameter("newEmail", newEmail);
			// query.setParameter("oldEmail", oldEmail);
			// query.executeUpdate();

			// commit transaction
			session.getTransaction().commit();

			return true;

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			return false;
			// e.printStackTrace();

		} finally {
			session.close();

		}

	}

	/*------------------------------------------------------------------------------------------------*/
	@Override
	public boolean deleteUser(Users user) {

		// call session
		session = DAOUtilities.getSession().openSession();

		try {
			tx = session.beginTransaction();

			session.delete(user);

			// commit transaction
			tx.commit();
			// session.getTransaction().commit();

			return true;

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			return false;
			// e.printStackTrace();

		} finally {
			session.close();

		}

	}

}
