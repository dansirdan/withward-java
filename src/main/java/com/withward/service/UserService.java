package com.withward.service;
import java.util.ArrayList;

import com.withward.model.User;
import com.withward.repository.UserRepository;

public class UserService {

	private UserRepository userRepository = new UserRepository();
	
	public ArrayList<User> getAllUsers() {
		return userRepository.getAll();
	}
	
	public User getOneUser(Integer userId) {
		return userRepository.getUser(userId);
	}
	
	public void createUser(User user) {
		userRepository.insertUser(user);
	}
	
	public void updateUser(User user) {
		userRepository.updateUser(user);
	}
	
	public void deleteUser(Integer userId) {
		userRepository.deleteOne(userId);
	}
}
