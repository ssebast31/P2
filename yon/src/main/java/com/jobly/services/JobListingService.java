package com.jobly.services;


import java.util.List;

import com.jobly.models.Company;
import com.jobly.models.JobListing;
import com.jobly.models.Place;

public interface JobListingService {
	boolean createListing(JobListing listing, Place place, Company company);

	boolean deleteListing(JobListing listing);

	JobListing getListing(long listingId);

	//get all job lsiting
	public List<JobListing> getAllJobListingByLocation(Place place);
	
	//update job listing
	public boolean updateJobListing(JobListing jobListing);
}
