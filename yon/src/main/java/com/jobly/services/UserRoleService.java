package com.jobly.services;

import java.util.List;

import com.jobly.models.UserRole;

public interface UserRoleService {
	int createRole(UserRole role);
	boolean deleteRole(UserRole role);
	UserRole getRole(String role);
	UserRole getRole(long id);
	boolean updateUserRoles(UserRole userRole);
	List<UserRole> getAllUserRoles();
}
