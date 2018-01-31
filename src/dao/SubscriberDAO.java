package dao;

import java.util.List;

import model.Author;
import model.Subscriber;
import model.Users;

public interface SubscriberDAO {
	
	public Subscriber createSubscriber(Author author, Users user);
	
	public boolean  removeSubscriber(int subscriberId); 
	
	public boolean  removeSubscriber( String userName, int authorId); 
	
	public Subscriber getSubscriberById(int subcriberId);
	
	public Subscriber getAllSubscriberById(int subcriberId);
	
	public List<Subscriber> getAllSubscriberForAnAuthor(String authorName);
	
	public Subscriber getSubscriberByUserName(String username);
	
	
	public Subscriber getAuthorSubscriber(String username, String authorName);
}
