package resources;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import dao.AuthorDAO;
import dao.AuthorDAOImpl;
import dao.MessageDAO;
import dao.MessageDAOImpl;
import dao.SubscriberDAO;
import dao.SubscriberDAOImpl;
import dao.UserDAO;
import dao.UserDAOImpl;
import model.Author;
import model.AuthorFiles;
import model.Books;
import model.Message;
import model.Subscriber;
import model.Users;

@Path("/service")
@Produces({ "application/json", "application/xml" })
@Consumes({ "application/json", "application/xml" })
public class Resource {

	AuthorDAO authordao = new AuthorDAOImpl();
	SubscriberDAO subscriberdao = new SubscriberDAOImpl();
	UserDAO userdao = new UserDAOImpl();
	ResponseBuilder respBuilder = null;
	Author author = null;
	Subscriber subscriber = null;
	MessageDAO messagedao = new MessageDAOImpl();
	Message message = null;

	@Context
	private UriInfo uriInfo;

	/*------------------------------------------------------------------------------------------------*/

	@GET
	@Path("/{authorName}")
	public Response getAuthorChannel(@PathParam("authorName") String authorName) {
		author = authordao.getAuthorbyAuthorName(authorName);

		List<Books> book = authordao.getBooksPublishedByAuthor(authorName);

	     List<Message> messages = messagedao.getAllMessagesforAuthor(authorName);
		if (author == null) {
			respBuilder = Response.status(Response.Status.NOT_FOUND).entity("Author does not exist");
		}

		else {

			author.addLink(getHATEAOSUri(author.getAuthorname()), "self");
			author.addLink(getHATEAOSUri(authorName + "/" + "books"), "books");
			author.addLink(getHATEAOSUri(authorName + "/" + "messages"), "messages");
			author.addLink(getHATEAOSUri(authorName + "/" + "subscribe"), "subscribe");
			author.addLink(getHATEAOSUri(authorName + "/" + "profile"), "profile");
			author.setBook(book);
			author.setMessages(messages);
			respBuilder = Response.ok().entity(author);

		}
		return respBuilder.build();

	}
	/*------------------------------------------------------------------------------------------------*/

	@GET
	@Path("/{authorName}/{userName}")
	public Response getAuthorChannel(@PathParam("authorName") String authorName,
			@PathParam("userName") String userName) {
		author = authordao.getAuthorbyAuthorName(authorName);

		List<Books> book = authordao.getBooksPublishedByAuthor(authorName);
		
		

		if (author == null) {
			respBuilder = Response.status(Response.Status.NOT_FOUND).entity("Author does not exist");
		}

		else {

			Subscriber subscribe = subscriberdao.getAuthorSubscriber(userName, authorName);
			if (subscribe != null) {
				author.setASubscriber(true);
				author.setSubscription(subscribe);
			}
			author.setBook(book);
			respBuilder = Response.ok().entity(author);

		}
		return respBuilder.build();

	}

	/*------------------------------------------------------------------------------------------------*/
	@GET
	@Path("/{authorName}/books/")
	public Response getAllBooksPublishedByAnAuthor(@PathParam("authorName") String authorName) {

		author = authordao.getAuthorbyAuthorName(authorName);

		List<Books> books = authordao.getBooksPublishedByAuthor(author.getAuthorid());

		if (author == null) {
			respBuilder = Response.status(Response.Status.NOT_FOUND).entity("Author does not exist");
		} else {

			author.addLink(getHATEAOSUri(String.valueOf(author.getAuthorid())), "author");

			respBuilder = Response.ok().entity(books);

		}
		return respBuilder.build();
	}

	/*------------------------------------------------------------------------------------------------*/
	@POST
	public Response createAuthor(String username) {

		Users user = userdao.getUserByusername(username);

		Author AuthorExist = authordao.getAuthorbyAuthorName(user.getFirstname() + " " + user.getLastname());

		if (AuthorExist != null) {
			respBuilder = Response.status(Response.Status.FOUND).entity(AuthorExist);
		} else {
			author = authordao.createAuthor(user);
			// automatic subscription of the author to his own channel so he can
			// post and view messages
			subscriber = subscriberdao.createSubscriber(author, user);

			URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(author.getAuthorid())).build();
			respBuilder = Response.created(uri).entity(author);
		}
		return respBuilder.build();
	}

	/*------------------------------------------------------------------------------------------------*/
	@PUT
	@Path("/{authorName}/subscribe/")
	public Response Subscription(@PathParam("authorName") String authorName, String userName) {

		Author author = authordao.getAuthorbyAuthorName(authorName);
		Users user = userdao.getUserByusername(userName);
		subscriber = subscriberdao.createSubscriber(author, user);
		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(subscriber.getSubcriberid())).build();
		respBuilder = Response.created(uri).entity("You have succesfully subscribe to this Author Channel!");

		return respBuilder.build();
	}

	/*------------------------------------------------------------------------------------------------*/
	@GET
	@Path("/{authorName}/subscribe/{userName}")
	public Response getSubscription(@PathParam("authorName") String authorName,
			@PathParam("userName") String userName) {

		subscriber = subscriberdao.getAuthorSubscriber(userName, authorName);

		if (subscriber == null) {
			respBuilder = Response.status(Response.Status.NOT_FOUND).entity(subscriber);
		} else {

			respBuilder = Response.ok().entity(subscriber);
		}

		return respBuilder.build();
	}

	/*------------------------------------------------------------------------------------------------*/

	@GET
	@Path("/authors/{userName}")
	public Response getAllAuthorsforUser(@PathParam("userName") String userName) {

		List<Author> author = authordao.getAllAuthors();

		for (Author temp : author) {
			

			if (subscriberdao.getAuthorSubscriber(userName, temp.getAuthorname()) != null) {
				temp.setASubscriber(true);

			}

		}

		if (author.isEmpty()) {
			respBuilder = Response.status(Response.Status.NOT_FOUND).entity("There are no Author Channels active!");
		} else {

			respBuilder = Response.status(Response.Status.OK).entity(author);
		}

		return respBuilder.build();

	}

	/*------------------------------------------------------------------------------------------------*/
	@DELETE
	@Path("/{authorName}/unsubscribe/{subscriberId}")
	public Response UnSubscription(@PathParam("authorName") String authorName,
			@PathParam("subscriberId") int subscriberId) {

		boolean unsubscribe = subscriberdao.removeSubscriber(subscriberId);

		if (unsubscribe == false) {
			respBuilder = Response.status(Response.Status.NOT_FOUND).entity("Error processing request");
		} else {

			respBuilder = Response.status(Response.Status.ACCEPTED)
					.entity("You have succesfully unsubscribe to this Author Channel!");
		}

		return respBuilder.build();
	}

	/*------------------------------------------------------------------------------------------------*/

	@GET
	@Path("/{authorName}/messages/")
	public Response getAllMessagesInAuthorChannel(@PathParam("authorName") String authorName) {

		List<Message> message = messagedao.getAllMessagesforAuthor(authorName);
		

		if (message.isEmpty()) {
			respBuilder = Response.status(Response.Status.NOT_FOUND).entity("There are no messages in Author Channel!");
		} else {

			respBuilder = Response.status(Response.Status.OK).entity(message);
		}

		return respBuilder.build();

	}
	/*------------------------------------------------------------------------------------------------*/

	@GET
	@Path("/message/{messageId}")
	public Response getMessageSend(@PathParam("messageId") int messageId) {

		Message message = messagedao.getMessageSend(messageId);
		

		if (message == null) {
			respBuilder = Response.status(Response.Status.NOT_FOUND)
					.entity("There is no message from you in Author Channel!");
		} else {
			
			int authorId = message.getAuthor().getAuthorid();
			String authorname =	message.getAuthor().getAuthorname();
			message.setAuthorId(authorId);
			message.setToAuthor(authorname);

			respBuilder = Response.status(Response.Status.OK).entity(message);
		}

		return respBuilder.build();

	}

	/*------------------------------------------------------------------------------------------------*/
	@POST
	@Path("/{authorName}/messages/")
	public Response addMessage(@PathParam("authorName") String authorName, Message message) {

		author = authordao.getAuthorbyAuthorName(authorName);
		// authordao.getAuthorbyAuthorName(authorName);
		String messageAuthor = message.getMessageAuthor();
		String content = message.getContent();

		if (messageAuthor.isEmpty()) {
			respBuilder = Response.status(Response.Status.UNAUTHORIZED).entity("No message author!");
		} else {
			message = messagedao.createMessage(author, messageAuthor, content);
			URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(message.getMessageid())).build();
			respBuilder = Response.created(uri).entity(message);
		}
		return respBuilder.build();
	}

	
	/*------------------------------------------------------------------------------------------------*/
	
	@POST
	@Consumes("application/octet-stream")
	@Path("/{authorName}/profile/")

	public Response addAuthorImage(@PathParam("authorName") String authorName, 
			InputStream is) throws Exception

	{

		AuthorFiles newProfile = new AuthorFiles();
		newProfile.setAuthor(authordao.getAuthorbyAuthorName(authorName));
		

		ByteArrayOutputStream os = null;

		try {

			os = new ByteArrayOutputStream();

			byte[] buffer = new byte[2048];

			while (is.read(buffer) != -1) {
				os.write(buffer);
			}

			newProfile.setPortrait(os.toByteArray());

		} catch (IOException e) {
			System.out.println("Could not upload file!");
			e.printStackTrace();
		} finally {
			if (is != null)
				is.close();
			if (os != null)
				os.close();
		}

		boolean isSuccess = authordao.createAuthorProfile(newProfile);

		if (isSuccess) {
			respBuilder = Response.status(Response.Status.CREATED).entity("File Uploaded Successfully");
		} else {
			respBuilder = Response.status(Response.Status.CONFLICT).entity("Error Uploading file");
		}

		return respBuilder.build();
	}
	
	/*------------------------------------------------------------------------------------------------*/
	@GET
	@Path("/profile/{authorId}")

	public Response getAuthorImage(@PathParam("authorId") int authorId) {
		
		AuthorFiles profile = authordao.getAuthorProfile(authorId);
		
		if (profile == null) {
			respBuilder = Response.status(Response.Status.NOT_FOUND).entity("Profile does not exist");
		} else {

			respBuilder = Response.ok().entity(profile);
		}

		return respBuilder.build();
	}
		
	
	/*------------------------------------------------------------------------------------------------*/
	private String getHATEAOSUri(String id) {
		String urirel = uriInfo.getBaseUriBuilder().path(Resource.class).path(id).build().toString();
		return urirel;
	}
}
