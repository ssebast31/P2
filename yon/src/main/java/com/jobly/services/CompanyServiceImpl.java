package com.jobly.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobly.models.Company;
import com.jobly.models.Place;
import com.jobly.models.User;
import com.jobly.repositories.CompanyRepository;
import com.jobly.repositories.PlaceRepository;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository crepo;

	@Autowired
	private PlaceRepository prepo;

	public CompanyServiceImpl(CompanyRepository dao, PlaceRepository pdao) {
		this.crepo = dao;
		this.prepo = pdao;
	}

	@Override
	public int createCompany(Company company, Place place) {
		if (crepo.existsByName(company.getName())) {
			return -1;
		}
		
		if (place != null) {
			// If the given place does not exist, add it to the places table, else read it
			// from the table.
			if (!prepo.existsByCityAndStateAndCountry(place.getCity(), place.getState(), place.getCountry())) {
				place = prepo.save(place);
				if (place.getId() < 1) {
					return 0;
				}
			} else {
				place = prepo.getByCityAndStateAndCountry(place.getCity(), place.getState(), place.getCountry());
			}

			// Companies place has to be assigned or it will be null.
		}

		company.setPlace(place);
		// return 1 if creation successful else 0
		long companyKey = crepo.save(company).getId();
		return companyKey > 0 ? 1 : 0;
	}

	@Override
	public boolean deleteCompany(Company company) {
		crepo.deleteById(company.getId());
		return true;
	}

	@Override
	public List<Company> getAllCompaniesByLocation(Place place) {
		Place target = place;
		if (place.getCity() == null || place.getCountry() == null) {
			target = prepo.getReferenceById(place.getId());
		}
		return crepo.findAllByLocation(target);
	}

	@Override
	public Company getCompanyById(Company company) {
		return crepo.getReferenceById(company.getId());
	}
	
	@Override
	public boolean updateCompany(Company company) {
		//return crepo.update(company.getName(), company.getEmail(), company.getPlace().getId(), company.getId());
		Company target = crepo.getReferenceById(company.getId());
		target.setName(company.getName());
		target.setEmail(company.getEmail());
		target.setId(company.getId());
		target.setPlace(company.getPlace());
		return (crepo.save(target) != null) ? true : false;
	}

	@Override
	public Company getCompanyByName(String name) {
		return crepo.getByName(name);
	}

	@Override
	public List<Company> getAllCompanies() {
		return crepo.findAll();
	}
}
