package dao;

import java.util.List;

import model.Author;
import model.Message;

public interface MessageDAO {
	
	public List<Message> getAllMessagesforAuthor(String authorName);
	
	public Message createMessage(Author author, String messageAuthor, String content);
	
	public Message getMessageSend(int messageId);

}
