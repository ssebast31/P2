package com.jobly.controllers;

import java.util.List;
import static com.jobly.util.ClientMessageUtil.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jobly.bodies.NewApplicationRequest;
import com.jobly.models.Application;
import com.jobly.models.ApplicationStatus;
import com.jobly.models.ClientMessage;
import com.jobly.models.JobListing;
import com.jobly.models.User;
import com.jobly.services.ApplicationService;
import com.jobly.services.ApplicationStatusService;

@CrossOrigin
@RestController
@RequestMapping("/api/Application")
public class ApplicationController {

	@Autowired
	private ApplicationService aservice;

	@Autowired
	private ApplicationStatusService appStatusService;
	
	@PostMapping("/application")
	public @ResponseBody ClientMessage createApplication(@RequestBody NewApplicationRequest nar) {
		Application application = nar.getApplication();
		User applicant = nar.getUser();
		long listingId = nar.getListingId();
		
		int code = aservice.createApplication(application, applicant, listingId);
		switch (code) {
		case 1:
			return CREATION_SUCCESSFUL;
		case 0:
			return CREATION_FAILED;
		}
		return null;
		
	}
	
	@DeleteMapping("/application")
	public @ResponseBody ClientMessage deleteApplication(@RequestBody Application application) {
		boolean result = aservice.deleteApplication(application); 
		return result ? DELETION_SUCCESSFUL : DELETION_FAILED ;
	}

	
	@PutMapping("/update")
	public ClientMessage updateApplication(@RequestBody Application application) {
		return aservice.updateCompany(application) ? UPDATE_SUCCESSFUL : UPDATE_FAILED;
		
	}

	@GetMapping("/findByID")
	public @ResponseBody Application getAppById(@RequestBody Application app){
		aservice.getApplicationById((int)app.getId());
		return app;

	}
	@GetMapping("/applicant/viewAll")
	public @ResponseBody List<Application> getAllAppsByUser(@RequestBody User userId) {
		List<Application> apps = aservice.getApplicationByUser(userId);
		return apps;
		
	}

	@GetMapping("/employer/viewAll")
	public @ResponseBody List<Application> getAllAppsByListing(@RequestBody JobListing listingId) {
		List<Application> apps = aservice.getApplicationByListing(listingId);
		return apps;
		
	}
	
}
