package com.jobly.controllers;

import static com.jobly.util.ClientMessageUtil.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.jobly.bodies.NewCompanyRequest;
import com.jobly.models.ClientMessage;
import com.jobly.models.Company;
import com.jobly.models.Place;
import com.jobly.services.CompanyService;

import java.util.List;

@CrossOrigin	
@RestController
@RequestMapping("/api/Company")
public class CompanyController {

	@Autowired
	private CompanyService cservice;
	
	// This class allows us to collect a company and a place in one request body
	static class CompanyAndPlace {
		private Company company;
		private Place place;
		public Company getCompany() { return this.company; }
		public Place getPlace() { return this.place; }
	}
	
	// Creates a company which references the provided place in its foreign key.
	// If the provided place does not exist it is added to the places table before
	// the company is created. If no place is provided the foreign key is left null.
	@PostMapping("/company")
	public @ResponseBody ClientMessage createCompany(@RequestBody NewCompanyRequest ncr) {

		Company company = ncr.getCompany();
		Place place = ncr.getPlace();
		int code = cservice.createCompany(company, place);

		switch (code) {
		case 1:
			return CREATION_SUCCESSFUL;
		case 0:
			return CREATION_FAILED;
		case -1:
			return COMPANY_NAME_ALREADY_EXISTS;
		}
		return null;
	}
	
	@DeleteMapping("/company")
	public @ResponseBody ClientMessage deleteCompany(@RequestBody Company company) {
		boolean result = cservice.deleteCompany(company);
		return result ? DELETION_SUCCESSFUL : DELETION_FAILED ;
	}
	//update method
	@PutMapping("/update")
	public @ResponseBody ClientMessage updateCompany(@RequestBody Company company) {
		return cservice.updateCompany(company) ? UPDATE_SUCCESSFUL : UPDATE_FAILED;
	}

	//get method
	@GetMapping("/findAllByLocation")
	public @ResponseBody List<Company> getAllCompanies(@RequestBody Place place){ 
		return cservice.getAllCompaniesByLocation(place);
	}
	
	@GetMapping("/company")
	public @ResponseBody Company getCompany(@RequestBody Company company) {
		if (company.getName() != null) {
			return cservice.getCompanyByName(company.getName());
		}
		
		if (company.getId() > 0) {
			return cservice.getCompanyById(company);
		}
		return null;
	}
}
