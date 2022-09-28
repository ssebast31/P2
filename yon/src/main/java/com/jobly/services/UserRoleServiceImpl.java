package com.jobly.services;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobly.models.UserRole;
import com.jobly.repositories.UserRoleRepository;

@Service("UserRoleService")
@Transactional
public class UserRoleServiceImpl implements UserRoleService {
	
	@Autowired
	private UserRoleRepository urrepo;
	
	public UserRoleServiceImpl(UserRoleRepository dao) {
		this.urrepo = dao;
	}

	@Override
	public int createRole(UserRole role) {
		if (urrepo.existsByRole(role.getRole())) {
			return -1;
		}
		
		// return 1 if creation is successful else 0
		long roleKey = urrepo.save(role).getId();
		return (roleKey > 0) ? 1 : 0;
	}
	
	@Override
	public boolean deleteRole(UserRole role) {
		urrepo.delete(role);
		return true;
	}

	@Override
	public UserRole getRole(String role) {
		return urrepo.getByRole(role);
	}

	@Override
	public UserRole getRole(long id) {
		return urrepo.getReferenceById(id);
	}

	@Override
	public List<UserRole> getAllUserRoles() {
		return urrepo.findAll();
	}

	@Override
	public boolean updateUserRoles(UserRole userRole) {
		//return urrepo.update(userRole.getRole(),userRole.getId());
				UserRole target = urrepo.getReferenceById(userRole.getId());
				target.setId(userRole.getId());
				target.setRole(userRole.getRole());
				return (urrepo.save(target) != null) ? true : false;
	}
}