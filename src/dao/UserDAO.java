package dao;

import java.util.List;

import model.Users;

/**
 * Interface for our User Access Object to handle database queries related to
 * Users and their roles.
 */
public interface UserDAO {

	public List<Users> getAllUsers();

	public Users getUserByEmail(String email);

	public Users getUserByusername(String username);

	

	public boolean updateUserEmail(String username, String email);

	public boolean deleteUser(Users user);

}
