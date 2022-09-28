package com.jobly.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jobly.models.UserRole;

@Repository
@Transactional
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	boolean existsByRole(String role);
	void deleteByRole(String role);
	UserRole getByRole(String role);

	//update user role
    @Query(value="UPDATE user_roles SET user_role=?1, WHERE user_role_id=?2", nativeQuery=true)
	public boolean update(String userRole,Long userRoleId);

}
