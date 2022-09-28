package com.jobly.services;

import java.util.List;

import com.jobly.models.Company;
import com.jobly.models.Place;


public interface CompanyService {
		int createCompany(Company company, Place place);
		
		boolean deleteCompany(Company company);

		//get all companies
		public List<Company> getAllCompaniesByLocation(Place place);
		
		//update company
		public boolean updateCompany(Company company);

		Company getCompanyById(Company company);

		Company getCompanyByName(String name);

		List<Company> getAllCompanies();
}
