package com.jobly.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobly.models.Place;
import com.jobly.models.User;
import com.jobly.repositories.UserRepository;
import com.jobly.repositories.UserRoleRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	

	@Autowired
	private UserRepository urepo;
	
	@Autowired
	private UserRoleRepository urrepo;
	
	public UserServiceImpl(UserRepository dao) {
		this.urepo = dao;
	}
	
	@Override
	public int createUser(User user, String role) {
		if (role == null) {
			return 0;
		}
		user.setRole(urrepo.getByRole(role));
		
		if (urepo.existsByUsername(user.getUsername())) {
			return -1;
		}
		
		if (urepo.existsByEmail(user.getEmail())) {
			return -2;
		}
		
		// Return 1 if creation successful, 0 if not
		long userKey = urepo.save(user).getId();
		return (userKey > 0) ? 1 : 0;
	}

	@Override
	public User login(String username, String password) {
		return urepo.getByUsernameAndPassword(username, password);
	}

	@Override
	public User getUser(String email) {
		return urepo.findUserByEmail(email);
	}

	@Override
	public User getUser(long id) {
		return urepo.getReferenceById(id);
	}

	@Override
	public User getUserBYEmail(String email) {
		User users = urepo.findUserByEmail(email);
		return users;
		//return urepo.findUserByEmail(email);
	}

	
}
