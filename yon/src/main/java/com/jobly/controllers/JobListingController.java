package com.jobly.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jobly.bodies.NewJobListingRequest;
import com.jobly.models.ClientMessage;
import com.jobly.models.Company;
import com.jobly.models.JobListing;
import com.jobly.models.ListingStatus;
import com.jobly.models.Place;
import com.jobly.services.JobListingService;
import com.jobly.services.ListingStatusService;

import static com.jobly.util.ClientMessageUtil.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/jobListing")
public class JobListingController {

	@Autowired
	private JobListingService jlservice;
	
	@Autowired
	private ListingStatusService lsservice;
		
	@PostMapping("/listing")
	public @ResponseBody ClientMessage createListing(@RequestBody NewJobListingRequest njlr) {
		JobListing listing = njlr.getListing();
		Place place = njlr.getPlace();
		Company company = njlr.getCompany();
		
		boolean result = jlservice.createListing(listing, place, company);
		return result ? CREATION_SUCCESSFUL : CREATION_FAILED ;
	}
	
	@DeleteMapping("/Listing")
	public @ResponseBody ClientMessage deleteListing(@RequestBody JobListing listing) {
		boolean result = jlservice.deleteListing(listing);
		return result ? DELETION_SUCCESSFUL : DELETION_FAILED;
	}
	
	@PostMapping("/ListingStatus")
	public @ResponseBody ClientMessage createListingStatus(@RequestBody ListingStatus status) {
		int code = lsservice.createStatus(status);
		switch (code) {
		case 1: 
			return CREATION_SUCCESSFUL;
		case 0: 
			return CREATION_FAILED;
		case -1:
			return ENTITY_ALREADY_EXISTS;
		}
		return null;
	}
	
	@DeleteMapping("/ListingStatus")
	public @ResponseBody ClientMessage deleteListingStatus(@RequestBody ListingStatus status) {
		boolean result = lsservice.deleteStatus(status);
		return result ? DELETION_SUCCESSFUL : DELETION_FAILED ;
	}

	//update method
	@PutMapping("/update")
	public ClientMessage updateJobListingStatus
	(@RequestBody JobListing jobListing) {
		return jlservice.updateJobListing(jobListing) ? UPDATE_SUCCESSFUL : UPDATE_FAILED;
	}

	//get method
	@GetMapping("/findAllByLocation")
	public List<JobListing> getAllCompaniesByLocation(@RequestBody Place place){
		return jlservice.getAllJobListingByLocation(place);
	}

}
