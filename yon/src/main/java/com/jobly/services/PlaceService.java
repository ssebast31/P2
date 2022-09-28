package com.jobly.services;

import java.util.List;

import com.jobly.models.Place;


public interface PlaceService {
	int createPlace(Place place);

	boolean deletePlace(Place place);
	
	//update place
	public boolean updatePlace(Place place);

	Place getPlaceById(long placeId);
	
	List<Place> getAllPlaces();

	List<Place> getAllPlacesByLocation(Place place);

}
