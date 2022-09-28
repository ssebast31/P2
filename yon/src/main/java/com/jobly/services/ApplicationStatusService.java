package com.jobly.services;

import java.util.List;

import com.jobly.models.ApplicationStatus;

public interface ApplicationStatusService {
    ApplicationStatus getStatus(long statusId);
    //create application status
    int createStatus(ApplicationStatus status);
     //retreive all applciation status
	public List<ApplicationStatus> getAllApplicationStatus();
    //update application status
	public boolean updateListingStatus(ApplicationStatus status);
	//delete aplication status
    boolean deleteStatus(ApplicationStatus status);
	 public static long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

   
	
	
	
}
