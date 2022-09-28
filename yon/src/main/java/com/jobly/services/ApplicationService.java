package com.jobly.services;

import java.util.List;

import com.jobly.models.Application;
import com.jobly.models.JobListing;
import com.jobly.models.User;

public interface ApplicationService {
	int createApplication(Application application, User applicant, long listingId);
	
	boolean deleteApplication(Application application);
	
	//finda all applications by id
	Application getApplicationById(long applicationId);
	
	//find all applications by user
	List<Application> getApplicationByUser(User user);
	
	//find aall alplication by listing 
	List<Application> getApplicationByListing(JobListing listing);
	
	//update application
	public boolean updateCompany(Application application);
}
