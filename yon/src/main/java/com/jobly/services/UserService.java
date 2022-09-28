package com.jobly.services;

import com.jobly.models.User;

public interface UserService {
	
	int createUser(User user, String role);
	User login(String username, String password);
	User getUser(String username);
	User getUser(long id);
	User getUserBYEmail(String email);
}
