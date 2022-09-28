package com.jobly.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobly.models.Application;
import com.jobly.models.ApplicationStatus;
import com.jobly.models.Company;
import com.jobly.models.JobListing;
import com.jobly.models.ListingStatus;
import com.jobly.models.Place;
import com.jobly.models.User;
import com.jobly.models.UserRole;
import com.jobly.repositories.ApplicationRepository;
import com.jobly.repositories.ApplicationStatusRepository;
import com.jobly.repositories.CompanyRepository;
import com.jobly.repositories.JobListingRepository;
import com.jobly.repositories.ListingStatusRepository;
import com.jobly.repositories.PlaceRepository;
import com.jobly.repositories.UserRepository;
import com.jobly.repositories.UserRoleRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Transactional
@Service
public class DemoDatabaseInitializerUtil {
	
	@Autowired
	private UserRoleRepository urrepo;
	
	@Autowired
	private ListingStatusRepository lsrepo;
	
	@Autowired
	private ApplicationStatusRepository asrepo;
	
	@Autowired
	private UserRepository urepo;
	
	@Autowired
	private PlaceRepository prepo;
	
	@Autowired
	private CompanyRepository crepo;
	
	@Autowired
	private JobListingRepository jlrepo;
	
	@Autowired
	private ApplicationRepository arepo;
	
	public void init() {
		UserRole employee = urrepo.getByRole("Employee");
		UserRole employer = urrepo.getByRole("Employer");
		
		ListingStatus open = lsrepo.getByStatus("Open");
		
		ApplicationStatus pending = asrepo.getByStatus("Pending");
		
		//Employee users
		User john     = urepo.save(new User("johntest", "John", "Test", "jtet@gmail.com", "password", employee, null));
		User carlita  = urepo.save(new User("somegirl", "Carlita", "Fakename", "carlfake@yahoo.yas", "password", employee, null));
		User victor   = urepo.save(new User("iceman", "Victor", "Friese", "vfreeze@gmail.com", "password", employee, null));
		User blamanda = urepo.save(new User("blgreen", "Blamanda", "Green", "bgreens@yahoo.hoo", "password", employee, null));
		
		//Places
		Place newYork    = prepo.save(new Place("New York", "New York", "USA"));
		Place losAngeles = prepo.save(new Place("Los Angeles", "California", "USA"));
		Place london     = prepo.save(new Place("London", null, "UK"));
		Place seattle    = prepo.save(new Place("Seattle", "Washington", "USA"));
		Place seoul      = prepo.save(new Place("Seoul", null, "South Korea"));
		Place nashtown   = prepo.save(new Place("nashtown", "Tennesee", "USA"));
		Place placeville = prepo.save(new Place("Placeville", "Statesota", "USA"));
		Place chicago    = prepo.save(new Place("Chicago", "The second worst midwestern state", "USA"));

		//Companies
		Company amyzone = crepo.save(new Company("amyzone", "amyzone@amyzone.com", losAngeles));
		Company eitc    = crepo.save(new Company("East India Trading Company", "no", london));
		Company techron = crepo.save(new Company("Techron", "techron@gamil.com" ,chicago));
		Company abandb  = crepo.save(new Company("abandb", "abandb@at.at", nashtown));
		Company shiraco = crepo.save(new Company("shiraco", "shiraco@gmail.com", placeville));
		
		//Employer users
		urepo.save(new User("bboy", "blondathan", "Rigby", "AAAAAAAAA", "password", employer, amyzone));
		urepo.save(new User("jsam", "Jeffica", "Sampson", "jsam@yahoo.mail", "password", employer, techron));
		urepo.save(new User("blimp", "Lomer", "Blimpson", "lomerblimpson@.com", "password", employer, abandb));
		urepo.save(new User("lordbeck", "Cutler", "Beckett", "cbeck@gmail.com", "password", employer, eitc));
		urepo.save(new User("msouth", "Mary Lou", "Southern", "", "password", employer, shiraco));

		//Job Listings
		JobListing listing1  = jlrepo.save(new JobListing("Feed the Amyzonian empire with your labor for compensation.", losAngeles, amyzone, open));
		JobListing listing4  = jlrepo.save(new JobListing("Feed the Amyzonian empire with your labor for compensation.", chicago, amyzone, open));
		JobListing listing5  = jlrepo.save(new JobListing("Feed the Amyzonian empire with your labor for compensation.", seoul, amyzone, open));
		JobListing listing10 = jlrepo.save(new JobListing("Feed the Amyzonian empire with your labor for compensation.", newYork, amyzone, open));
		JobListing listing11 = jlrepo.save(new JobListing("Feed the Amyzonian empire with your labor for compensation.", placeville, amyzone, open));
		JobListing listing12 = jlrepo.save(new JobListing("Feed the Amyzonian empire with your labor for compensation.", seattle, amyzone, open));
		JobListing listing7  = jlrepo.save(new JobListing("Feed the Amyzonian empire with your labor for compensation.", london, amyzone, open));
		JobListing listing14 = jlrepo.save(new JobListing("Feed the Amyzonian empire with your labor for compensation.", nashtown, amyzone, open));
		JobListing listing6  = jlrepo.save(new JobListing(".", losAngeles, amyzone, open));
		
		JobListing listing2  = jlrepo.save(new JobListing("We need a jigget man to jig the jiggets", london, eitc, open));
		
		JobListing listing3  = jlrepo.save(new JobListing("Astrological homologist needed.", chicago, abandb, open));
		
		JobListing listing8  = jlrepo.save(new JobListing("Find pride in web development with Techron.", losAngeles, techron, open));
		JobListing listing9  = jlrepo.save(new JobListing("Come program for techron, spicy benefits included.", chicago, techron, open));
		JobListing listing13 = jlrepo.save(new JobListing("Do IT for Techron and find a community.", placeville,techron, open));
		
		//Applications
		arepo.save(new Application(john, new byte[] {1, 2, 3}, listing1, pending));
		arepo.save(new Application(victor, new byte[] {4, 5, 7}, listing3, pending));
		arepo.save(new Application(john, new byte[] {1, 2, 3}, listing5, pending));
		arepo.save(new Application(carlita, new byte[] {9, 9, 9}, listing3, pending));
		arepo.save(new Application(blamanda, new byte[] {3, 4, 5}, listing3, pending));
		
	}
}
