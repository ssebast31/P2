package com.jobly.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jobly.models.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

	User getByUsernameAndPassword(String username, String password);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);
	
	//retrieve user by email
	@Query(value="SELECT * FROM users WHERE email=?1", nativeQuery = true)
	public User findUserByEmail(String email);

	User getByUsername(String username);
}
