package dao;

import java.util.List;

import model.Author;
import model.AuthorFiles;
import model.Users;


public interface AuthorDAO extends BookDAO {
	
	public Author createAuthor( Users user);
	
	public Author getAuthorbyAuthorName(String authorName);
	
	public Author getAuthorbyID(int authorID);
	
	public List<Author> getAllAuthors();
	
	public boolean createAuthorProfile(AuthorFiles newProfile);
	
	public AuthorFiles getAuthorProfile(int authorId);
	
	
	
}
