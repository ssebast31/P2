package com.jobly.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jobly.models.Company;
import com.jobly.models.Place;

@Repository
@Transactional
public interface CompanyRepository extends JpaRepository<Company, Long> {
	//retirves all companies by location 
	@Query(value="SELECT * FROM companies WHERE location=?1", nativeQuery = true)
	public List<Company> findAllByLocation(Place place);

	 //update all properies of a company
	@Query(value="UPDATE companies SET company_name=?1, email=?2, place_id=?3 WHERE company_id=?4", nativeQuery=true)
	public boolean update(String name, String email, Long placeId, Long companyId);
	
	boolean existsByName(String name);

	public Company getByName(String name);

}
