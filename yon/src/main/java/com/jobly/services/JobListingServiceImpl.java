package com.jobly.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobly.models.Company;
import com.jobly.models.JobListing;
import com.jobly.models.Place;
import com.jobly.repositories.CompanyRepository;
import com.jobly.repositories.JobListingRepository;
import com.jobly.repositories.ListingStatusRepository;
import com.jobly.repositories.PlaceRepository;

@Service
@Transactional
public class JobListingServiceImpl implements JobListingService {
	
	@Autowired
	private JobListingRepository jlrepo;
	
	@Autowired
	private ListingStatusRepository lsrepo;
	
	@Autowired
	private PlaceRepository prepo;
	
	@Autowired
	private CompanyRepository crepo;
	
	public JobListingServiceImpl(JobListingRepository dao) {
		this.jlrepo = dao;
	}
	
	@Override
	public boolean createListing(JobListing listing, Place place, Company company) {
		if (place != null) {
			// If the given place does not exist, add it to the places table, else read it
			// from the table.
			if (!prepo.existsByCityAndStateAndCountry(place.getCity(), place.getState(), place.getCountry())) {
				long placeKey = prepo.save(place).getId();
				if (placeKey < 1) {
					return false;
				}
			} else {
				place = prepo.getByCityAndStateAndCountry(place.getCity(), place.getState(), place.getCountry());
			}

			// The listings place has to be assigned or it will be null.
			listing.setPlace(place);
		}
		
		long companyId = company.getId();
		String companyName = company.getName();
		if (companyId < 1 && companyName == null) {
			return false;
		} else if (companyId > 0) {
			company = crepo.getReferenceById(companyId);
		} else {
			company = crepo.getByName(companyName);
		}
		
		listing.setStatus(lsrepo.getByStatus("Open"));
		
		long listingKey = jlrepo.save(listing).getId();
		return (listingKey > 0);
	}

	@Override
	public boolean deleteListing(JobListing listing) {
		jlrepo.delete(listing);
		return true;
	}

	@Override
	public JobListing getListing(long listingId) {
		return jlrepo.getReferenceById(listingId);
	
	}


	@Override
	public List<JobListing> getAllJobListingByLocation(Place place) {
		return jlrepo.findAllByLocation(place.getId());
	}
	

	@Override
	public boolean updateJobListing(JobListing jobListing) {
		JobListing target = jlrepo.getReferenceById(jobListing.getId());
		target.setStatus(jobListing.getStatus());
	
		return (jlrepo.save(target) != null) ? true : false;
	}
}
