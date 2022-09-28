package com.jobly.controllers;

import static com.jobly.util.ClientMessageUtil.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jobly.bodies.LoginRequest;
import com.jobly.bodies.NewUserRequest;
import com.jobly.models.ClientMessage;
import com.jobly.models.User;
import com.jobly.models.UserRole;
import com.jobly.services.UserRoleService;
import com.jobly.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api/User")
public class UserController {


	@Autowired
	private UserService uservice;

	@Autowired
	private UserRoleService urservice;

	@PostMapping("/userRole")
	public @ResponseBody ClientMessage createUserRole(@RequestBody UserRole role) {
		int code = urservice.createRole(role);
		switch (code) {
		case 1:
			return CREATION_SUCCESSFUL;
		case 0:
			return CREATION_FAILED;
		case -1:
			return ENTITY_ALREADY_EXISTS;
		}
		
		return null;
	}

	@DeleteMapping("/userRole")
	public @ResponseBody ClientMessage deleteUserRole(@RequestBody UserRole role) {
		boolean result = urservice.deleteRole(role);
		return result ? DELETION_SUCCESSFUL : DELETION_FAILED;
	}

	@PostMapping("/account/register")
	public @ResponseBody ClientMessage createUser(@RequestBody NewUserRequest nur) {
		String role = nur.getRole();
		User user = nur.getUser();
	
		int code = uservice.createUser(user, role);
		switch (code) {
		case 1:
			return CREATION_SUCCESSFUL;
		case 0:
			return CREATION_FAILED;
		case -2:
			return USERNAME_ALREADY_EXISTS;
		case -3:
			return EMAIL_ALREADY_EXISTS;
		}

		return null;
	}

	@PostMapping("/login")
	public @ResponseBody User authenticateLogin(@RequestBody LoginRequest lr) {
		return uservice.login(lr.getIdentifier(), lr.getPassword());
	}
	

	//get method
	@GetMapping("/findUserByEmail")
	public User getUser(@RequestBody String email){
		return uservice.getUserBYEmail(email);
	}
	
}
