package com.jobly.services;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

import com.jobly.models.Place;
import com.jobly.repositories.PlaceRepository;

@Service
@Transactional
public class PlaceServiceImpl implements PlaceService {
	
	@Autowired
	private PlaceRepository psrepo;
	
	public PlaceServiceImpl(PlaceRepository dao) {
		this.psrepo = dao;
	}

	@Override
	public int createPlace(Place place) {
		// If this place already exists in the table return -1
		if(psrepo.existsByCityAndStateAndCountry(place.getCity(), place.getState(), place.getCountry())) {
			return -1;
		}
		
		// Return 1 if successful, 0 if not
		long placeKey = psrepo.save(place).getId();
		return (placeKey > 0) ? 1 : 0;
	}


	@Override
	public boolean deletePlace(Place place) {
		psrepo.delete(place);
		return true;
	}

	@Override
	public boolean updatePlace(Place place) {
		return psrepo.update(place.getCity(), place.getState(), place.getCountry(), place.getId());
	}

	@Override
	public Place getPlaceById(long placeId) {
		return psrepo.getReferenceById(placeId);
	}
	
	@Override
	public List<Place> getAllPlaces() {
		return psrepo.findAll();
	}

	@Override
	public List<Place> getAllPlacesByLocation(Place place) {
		List<Place> places = psrepo.findAllByLocation(place.getState(), place.getCountry());
        return places;
	}

}
