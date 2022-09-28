package com.jobly.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jobly.models.Place;

@Repository
@Transactional
public interface PlaceRepository extends JpaRepository<Place, Long> {

	boolean existsByCityAndStateAndCountry(String city, String state, String country);

	Place getByCityAndStateAndCountry(String city, String state, String country);

	//update place
    @Query(value="UPDATE places SET city=?1, state=?2, country=?3, WHERE place_id=?4", nativeQuery=true)
	public boolean update(String city, String state, String country,  Long id);
	//get all places by state and country 
	@Query(value="SELECT * FROM places WHERE state=?1 AND country=?2", nativeQuery=true)
	public List<Place> findAllByLocation(String state, String Country);




}
