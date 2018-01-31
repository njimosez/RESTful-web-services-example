package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.Author;
import model.Subscriber;
import model.Users;
import utilities.DAOUtilities;

public class SubscriberDAOImpl implements SubscriberDAO {
	Session session = null;
	Transaction tx = null;

	/*------------------------------------------------------------------------------------------------*/

	@Override
	public Subscriber createSubscriber(Author author, Users user) {

		// call session
		session = DAOUtilities.getSession().openSession();

		try {

			// create instance of subscriber object and set properties
			Subscriber subscriber = new Subscriber();
			subscriber.setAuthor(author);
			subscriber.setUsers(user);
			subscriber.setSubscribername(user.getUsername());

			// start transaction
			tx = session.beginTransaction();

			// save session
			session.save(subscriber);

			// commit transaction
			tx.commit();

			// session.getTransaction().commit();

			// return the persisted object if successful

			return subscriber;

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			// return null if error

			return null;

		} finally {

			session.close();

		}

	}

	/*------------------------------------------------------------------------------------------------*/
	@Override
	public boolean removeSubscriber(int subscriberId) {
		
		Subscriber subscriber = getSubscriberById(subscriberId);
		session = DAOUtilities.getSession().openSession();
		
		try {
			tx = session.beginTransaction();

			

			// delete using session.delete method

			session.delete(subscriber);

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
	public Subscriber getSubscriberById(int subscriberId) {

		Subscriber subscriber = new Subscriber();
		// call a session
		session = DAOUtilities.getSession().openSession();

		try {

			tx = session.beginTransaction();
			subscriber = session.get(Subscriber.class, subscriberId);
			// commit transaction
			session.getTransaction().commit();

			return subscriber;

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();

		}

	}

	/*------------------------------------------------------------------------------------------------*/
	@Override
	public Subscriber getAllSubscriberById(int subscriberId) {
		// TODO Auto-generated method stub
		return null;
	}
	/*------------------------------------------------------------------------------------------------*/
	@Override
	public List<Subscriber> getAllSubscriberForAnAuthor(String authorName) {

		
		// call a session
		session = DAOUtilities.getSession().openSession();

		try {
			List <Subscriber> fans = new ArrayList<>();
			tx = session.beginTransaction();
	
			TypedQuery<Subscriber> lQuery = session.createQuery("FROM Subscriber s where author.authorname = :authorName", Subscriber.class);
			lQuery.setParameter("authorName", authorName);
		    fans = lQuery.getResultList();
			

			// commit transaction
			session.getTransaction().commit();

			//return lQuery.equals(lQuery);
			return fans;

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			return null;
			
		} finally {
			session.close();

		}

	}
	/*------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("rawtypes")
	@Override
	public Subscriber getSubscriberByUserName(String username) {
		
		// call a session
		session = DAOUtilities.getSession().openSession();

		try {
			Subscriber subscriber = new Subscriber();
			tx = session.beginTransaction();
	
			Query query = session.createQuery("FROM Subscriber s where users.username= :username");
			query.setParameter("username", "username");
		    subscriber = (Subscriber) query.uniqueResult();
			

			// commit transaction
			session.getTransaction().commit();

			//return lQuery.equals(lQuery);
			return subscriber;

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			return null;
			
		} finally {
			session.close();

		}


}
	
	/*------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("rawtypes")
	@Override
	public Subscriber getAuthorSubscriber(String username, String authorName) {
		// call a session
				session = DAOUtilities.getSession().openSession();
				Subscriber subscriber = new Subscriber();
				try {
					
					tx = session.beginTransaction();
			
					Query query = session.createQuery("FROM Subscriber s where s.subscribername = :username and author.authorname = :authorname");
					query.setParameter("username", username);
					query.setParameter("authorname", authorName);
				   
					 subscriber = (Subscriber) query.uniqueResult();

					// commit transaction
					session.getTransaction().commit();
					

					//return lQuery.equals(lQuery);
					return subscriber;

				} catch (HibernateException e) {
					if (tx != null)
						tx.rollback();
					e.printStackTrace();
					return null;
					
				} finally {
					session.close();

				}
				
				
	}
	/*------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("rawtypes")
	@Override
	public boolean removeSubscriber(String userName, int authorId) {
		
		
		// call a session
		session = DAOUtilities.getSession().openSession();

		try {

			tx = session.beginTransaction();

			Query query = session.createQuery("delete Subscriber where subscribername = :username and author.authorid = :authorId");
			query.setParameter("username", userName);
			query.setParameter("authorId", authorId);
			int result = query.executeUpdate();
			
			// commit transaction
			session.getTransaction().commit();
			if (result > 0) {
				return true;
			}
			else {
				return false;
			}


		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			return false;

		} finally {
			session.close();

		}
		
	}
}