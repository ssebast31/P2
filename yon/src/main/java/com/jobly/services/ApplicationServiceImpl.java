package com.jobly.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobly.models.Application;
import com.jobly.models.JobListing;
import com.jobly.models.User;
import com.jobly.repositories.ApplicationRepository;
import com.jobly.repositories.ApplicationStatusRepository;
import com.jobly.repositories.JobListingRepository;
import com.jobly.repositories.UserRepository;


@Service("AppicationService")
@Transactional
public class ApplicationServiceImpl implements ApplicationService {
	//application repository 
	@Autowired
	private ApplicationRepository arrepo;
	
	@Autowired
	private ApplicationStatusRepository asrrepo;
	
	@Autowired
	private JobListingRepository jlrepo;
	
	@Autowired
	private UserRepository urepo;
	
	public ApplicationServiceImpl(ApplicationRepository dao, ApplicationStatusRepository dao2, JobListingRepository dao3, UserRepository dao4) {
		this.arrepo = dao;
		this.asrrepo = dao2;
		this.jlrepo = dao3;
		this.urepo = dao4;
	}

	@Override
	public List<Application> getApplicationByUser(User user) {
		List<Application> userApplications = arrepo.findAllByUser(user.getId());
        return userApplications;
	}

	@Override
	public int createApplication(Application application, User applicant, long listingId) {
		application.setStatus(asrrepo.getByStatus("Pending"));
		
		if (listingId < 1) {
			return 0;
		}
		application.setListing(jlrepo.getReferenceById(listingId));
		
		String username = applicant.getUsername();
		long userId = applicant.getId();
		if (username == null && userId < 1) {
			return 0;
		} else if (userId > 0) {
			application.setApplicant(urepo.getReferenceById(userId));
		} else {
			application.setApplicant(urepo.getByUsername(username));
		}
		
		long applicationKey = arrepo.save(application).getId();
		return (applicationKey > 0) ? 1 : 0;
	}

	@Override
	public boolean deleteApplication(Application application) {
		arrepo.delete(application);
		return true;
	}


	@Override
	public List<Application> getApplicationByListing(JobListing listing) {
		List<Application> applications = arrepo.findAllByListing(listing.getId());
		return applications;
	}


	@Override
	public Application getApplicationById(long application_id) {
		Application app =arrepo.getReferenceById(application_id);
		return app;
	}


	@Override
	public boolean updateCompany(Application application) {
		//return arrepo.update(application.getStatus().getId(), application.getId());
		Application target = arrepo.getReferenceById(application.getId());
		target.setId(application.getId());
		target.setStatus(application.getStatus());
		return (arrepo.save(target) != null) ? true : false;
	}

}
