package dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.Author;
import model.Message;
import utilities.DAOUtilities;

public class MessageDAOImpl implements MessageDAO {
	Session session = null;
	Transaction tx = null;

	/*------------------------------------------------------------------------------------------------*/
	@Override
	public List<Message> getAllMessagesforAuthor(String authorName) {

		List<Message> messages = new ArrayList<>();

		// call a session
		session = DAOUtilities.getSession().openSession();

		try {
			tx = session.beginTransaction();

			@SuppressWarnings("unchecked")

			Query<Message> query = session.createQuery("FROM Message m where author.authorname= :authorName");
			query.setParameter("authorName", authorName);

			messages = query.getResultList();
			// commit transaction
			session.getTransaction().commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return messages;
	}
	/*------------------------------------------------------------------------------------------------*/
	@Override
	public Message createMessage(Author author, String messageAuthor, String content) {
		LocalDate datecreated = LocalDate.now();
	
		// call session
				session = DAOUtilities.getSession().openSession();

				try {

					// create instance of author object and set properties
					Message message = new Message();
					message.setAuthor(author);
					message.setContent(content);
					message.setDatecreated(datecreated);
					message.setMessageAuthor(messageAuthor);
					

					// start transaction
					tx = session.beginTransaction();

					// save session
					session.save(message);

					// commit transaction
					tx.commit();

					// session.getTransaction().commit();

					// return the persisted object if successful

					return message;

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
	public Message getMessageSend(int messageId) {
		
		Message message = new Message();
		session = DAOUtilities.getSession().openSession();

		try {

			tx = session.beginTransaction();
			message = session.get(Message.class, messageId);
			// commit transaction
			session.getTransaction().commit();

			return message;

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();

		}

	}

	}


