package main.java.service;

import main.java.dao.UserDAO;
import main.java.model.User;

public class UserService {

	private UserDAO userDAO;
	
	public UserService() {
		this.userDAO = new UserDAO();
	}
	
	public boolean register(User user) {
		return userDAO.createUser(user);
	}
	
	public boolean loginValidate(String  username, String password) {
		return userDAO.loginValidation(username, password);
	}
	
	public boolean usernameValidation(String username) {
		return userDAO.checkUsernameExists(username);
	}
	
	public User getUserInfo(String username, String password) {
		return userDAO.getUserByUsernameAndPassword(username, password);
	}
	

}
