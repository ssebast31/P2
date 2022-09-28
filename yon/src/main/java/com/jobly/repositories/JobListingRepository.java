package com.jobly.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jobly.models.JobListing;


@Repository
@Transactional
public interface JobListingRepository extends JpaRepository<JobListing, Long> {
    //update status of job posting
    @Query(value="UPDATE job_listing SET status_id=?1, WHERE listing_id=?2", nativeQuery=true)
	public boolean update(Long listingStatus, Long listingId);
	//get all the job listing from a place 
	@Query(value="SELECT * FROM job_listing WHERE place_id=?1", nativeQuery=true)
	public List<JobListing> findAllByLocation(Long locationId);



}
