package com.jobly.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jobly.models.Application;

@Repository
@Transactional
public interface ApplicationRepository extends JpaRepository<Application, Long> {
	

	 @Query(value="SELECT * FROM applications WHERE application_id=?", nativeQuery = true)
	 public Application findById(int id);
	 
	 //query apps by the applicaint id
	 //this can be used on the user side 
	 @Query(value="SELECT * FROM applications WHERE applicant_id=?1", nativeQuery = true)
	 public List<Application> findAllByUser(Long userId);
	 
	 //query apps by the listing id
	 //this can be used on the employoer side 
	 @Query(value="SELECT * FROM applications WHERE listing_id=?1", nativeQuery = true)
	 public List<Application> findAllByListing(Long listingId);

	 //update status of application
	@Query(value="UPDATE applications SET status_id=?1 WHERE application_id=?2", nativeQuery=true)
	public boolean update(Long status_id, Long id);


	 


	
}
