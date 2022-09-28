package com.jobly.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jobly.models.ApplicationStatus;

@Repository
@Transactional
public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Long> {
    boolean existsByStatus(String status);
	boolean deleteByStatus(String status);

	//update listing status 
    @Query(value="UPDATE application_status SET status=?1, WHERE status_id=?2", nativeQuery=true)
    public boolean update(String status, Long id);
	ApplicationStatus getByStatus(String string);
	
}
