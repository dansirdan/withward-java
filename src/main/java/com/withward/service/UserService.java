package com.withward.service;
import java.sql.SQLException;
import java.util.ArrayList;

import com.withward.model.User;
import com.withward.repository.UserDAO;

public class UserService {

	private UserDAO userRepository = new UserDAO();
	
	public ArrayList<User> getAllUsers() throws SQLException{
		return userRepository.getAll();
	}
	
	public boolean isAuthenticated(String username, String password) throws SQLException{
		return userRepository.authenticateUser(username, password);
	}
	
	public User getOneUser(Integer userId)throws SQLException {
		return userRepository.getUser(userId);
	}
	
	public User getByUsername(String username) throws SQLException {
		return userRepository.getUserByUsername(username);
	}
	
	public User createUser(User user)throws SQLException {
		return userRepository.insertUser(user);
	}
	
	public User updateUser(User user, Integer id)throws SQLException {
		return userRepository.updateUser(user, id);
	}
	
	public void deleteUser(Integer userId)throws SQLException {
		userRepository.deleteOne(userId);
	}
}
