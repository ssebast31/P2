package com.jobly.services;

import java.util.List;
import com.jobly.models.ListingStatus;

public interface ListingStatusService {
	int createStatus(ListingStatus status);
	boolean deleteStatus(ListingStatus status);
	ListingStatus getStatusById(Long id);

	//get all listing status 
	public List<ListingStatus> getAllListingStatus();
	
	
	//update job listing
	public boolean updateListingStatus(ListingStatus listing);
}
