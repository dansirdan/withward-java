package com.withward.service;
import java.util.ArrayList;

import com.withward.model.User;
import com.withward.repository.UserDAO;

public class UserService {

	private UserDAO userRepository = new UserDAO();
	
	public ArrayList<User> getAllUsers() {
		return userRepository.getAll();
	}
	
	public boolean isAuthenticated(String username, String password) {
		return userRepository.authenticateUser(username, password);
	}
	
	public User getOneUser(Integer userId) {
		return userRepository.getUser(userId);
	}
	
	public User createUser(User user) {
		return userRepository.insertUser(user);
	}
	
	public User updateUser(User user) {
		return userRepository.updateUser(user);
	}
	
	public void deleteUser(Integer userId) {
		userRepository.deleteOne(userId);
	}
}
