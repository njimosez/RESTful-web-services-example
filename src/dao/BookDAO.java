package dao;

import java.util.List;

import model.Books;


public interface BookDAO {

	public List<Books> getBooksPublishedByAuthor(int authorID);
	public List<Books> getBooksPublishedByAuthor(String authorName);
	public List<Books> getAllBooks();
	public Books getBookByISBN(String isbn);
	

}