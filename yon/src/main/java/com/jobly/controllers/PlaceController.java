package com.jobly.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.jobly.models.ClientMessage;
import com.jobly.models.Place;
import com.jobly.services.PlaceService;
import static com.jobly.util.ClientMessageUtil.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/Place")
public class PlaceController {
	
	@Autowired
	private PlaceService pservice;
	
	@PostMapping("/place")
	public @ResponseBody ClientMessage createPlace(@RequestBody Place place) {
		int code = pservice.createPlace(place);
		switch (code) {
		case 1:
			return CREATION_SUCCESSFUL;
		case 0:
			return CREATION_FAILED;
		case -1:
			return ENTITY_ALREADY_EXISTS;
		}
		
		return null;
	}
	
	@DeleteMapping("/place")
	public @ResponseBody ClientMessage deletePlace(@RequestBody Place place) {
		boolean result = pservice.deletePlace(place);
		return result ? DELETION_SUCCESSFUL : DELETION_FAILED;
	}

	//update method
	@PutMapping("/update")
	public ClientMessage updatePlace(@RequestBody Place place) {
		return pservice.updatePlace(place) ? UPDATE_SUCCESSFUL : UPDATE_FAILED;
	}
	
	@GetMapping("/place")
	public @ResponseBody Place getPlace(@RequestBody long placeId) {
		return pservice.getPlaceById(placeId);
	}
	
	@GetMapping("/allPlaces")
	public @ResponseBody List<Place> getAllPlaces() {
		return pservice.getAllPlaces();

	}

	//get method
	@GetMapping("/findAllByLocation")
	public List<Place> getAllPlaces(@RequestBody Place place){
		return pservice.getAllPlacesByLocation(place);

	}
}

