package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.Author;
import model.AuthorFiles;
import model.Books;
import model.Users;
import utilities.DAOUtilities;

public class AuthorDAOImpl implements AuthorDAO {

	Session session = null;
	Transaction tx = null;

	/*------------------------------------------------------------------------------------------------*/

	@Override
	public Author createAuthor(Users user) {

		// call session
		session = DAOUtilities.getSession().openSession();

		try {
			// if (getAuthorbyAuthorName(book)
			// create instance of author object and set properties
			Author author = new Author();
			author.setAuthorname(user.getFirstname() + " " + user.getLastname());
			author.setUsers(user);

			// start transaction
			tx = session.beginTransaction();

			// save session
			session.save(author);

			// commit transaction
			tx.commit();

			// session.getTransaction().commit();

			// return the persisted object if successful

			return author;

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
	public Books getBookByISBN(String isbn) {
		Books book = null;

		// call a session
		session = DAOUtilities.getSession().openSession();

		try {
			tx = session.beginTransaction();
			book = session.get(Books.class, isbn);
			// commit transaction
			session.getTransaction().commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();

		}

		return book;
	}

	/*------------------------------------------------------------------------------------------------*/

	@Override
	public Author getAuthorbyAuthorName(String authorName) {

		Author author = null;

		// call a session
		session = DAOUtilities.getSession().openSession();

		try {
			tx = session.beginTransaction();

			@SuppressWarnings("rawtypes")
			Query query = session.createQuery("FROM Author a where a.authorname= :authorName");
			query.setParameter("authorName", authorName);
			// get Unique result using result type that matches the query
			author = (Author) query.uniqueResult();

			// commit transaction
			session.getTransaction().commit();
			return author;

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
	public Author getAuthorbyID(int authorID) {
		Author author = null;

		// call a session
		session = DAOUtilities.getSession().openSession();

		try {

			tx = session.beginTransaction();
			author = session.get(Author.class, authorID);
			// commit transaction
			session.getTransaction().commit();

			return author;

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
	public List<Author> getAllAuthors() {
		List<Author> authors = new ArrayList<>();

		// call a session
		session = DAOUtilities.getSession().openSession();

		try {
			tx = session.beginTransaction();
			
			authors= session.createQuery("FROM Author", Author.class).getResultList();
			
			session.getTransaction().commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return authors;
	}
	/*------------------------------------------------------------------------------------------------*/

	@Override
	public List<Books> getBooksPublishedByAuthor(int authorId) {
		List<Books> books = new ArrayList<>();

		// call a session
		session = DAOUtilities.getSession().openSession();

		try {
			tx = session.beginTransaction();

			TypedQuery<Books> lQuery = session.createQuery("FROM Books b where author.authorid= :authorid",
					Books.class);
			lQuery.setParameter("authorid", authorId);

			// commit transaction
			session.getTransaction().commit();
			books = lQuery.getResultList();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return books;
	}
	/*------------------------------------------------------------------------------------------------*/
	@Override
	public List<Books> getAllBooks() {

		List<Books> books = new ArrayList<>();

		// Create Session

		session = DAOUtilities.getSession().openSession();

		try {
			tx = session.beginTransaction();
			TypedQuery<Books> lQuery = session.createQuery("FROM Books", Books.class);
			books = lQuery.getResultList();

			// commit transaction
			session.getTransaction().commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();

		}
		// return the list of Book objects populated by the DB.
		return books;
	}
	/*------------------------------------------------------------------------------------------------*/
	
	@Override
	public List<Books> getBooksPublishedByAuthor(String authorName) {
		List<Books> books = new ArrayList<>();

		// call a session
		session = DAOUtilities.getSession().openSession();

		try {
			tx = session.beginTransaction();

			
			
			@SuppressWarnings("unchecked")
			Query<Books> query = session.createQuery("FROM Books b where b.authorName = :authorName");
			query.setParameter("authorName", authorName);
			
			books = query.getResultList();
			// commit transaction
			session.getTransaction().commit();
			
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return books;
	}
	/*------------------------------------------------------------------------------------------------*/

	@Override
	public boolean createAuthorProfile(AuthorFiles newProfile) {
		// call session
				session = DAOUtilities.getSession().openSession();

				try {
					

					// start transaction
					tx = session.beginTransaction();

					// save session
					session.save(newProfile);

					// commit transaction
					tx.commit();

					return true;

				} catch (HibernateException e) {
					if (tx != null)
						tx.rollback();
					e.printStackTrace();
					// return null if error

					return false;

				} finally {

					session.close();

				}
			}
	/*------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	@Override
	public AuthorFiles getAuthorProfile(int authorId) {
		
		//Author author = getAuthorbyAuthorName(authorName);
		//int authorId = author.getAuthorid();
		
		AuthorFiles profile = new AuthorFiles();
	

		// call a session
		session = DAOUtilities.getSession().openSession();

		try {
			tx = session.beginTransaction();

			Query<AuthorFiles> query = session.createQuery("FROM AuthorFiles a where author.authorid = :authorid");
			query.setParameter("authorid", authorId);
			profile = query.uniqueResult();
			// commit transaction
			session.getTransaction().commit();
			
			
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return profile;
	}

	
		}