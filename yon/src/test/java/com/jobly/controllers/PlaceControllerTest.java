package com.jobly.controllers;

import static com.jobly.util.ClientMessageUtil.DELETION_SUCCESSFUL;
import static com.jobly.util.ClientMessageUtil.UPDATE_SUCCESSFUL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobly.models.Place;
import com.jobly.services.PlaceService;
import com.jobly.util.ClientMessageUtil;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PlaceController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlaceControllerTest {
	private static Place mockPlace1;
	private static Place mockPlace2;
	private static Place mockPlaceCreation;
	private static Place mockPlaceModification;
	private static Place mockPlaceDeletion;
	private static List<Place> dummyDb;

	ObjectMapper om = new ObjectMapper();

	@Autowired
	PlaceController placeController;
	
    @Autowired
	private MockMvc mockmvc;


	@MockBean
	private static PlaceService Pservice;
    
    @SuppressWarnings("deprecation")
	public boolean isValidJSON(final String json) {
		boolean valid = false;
		try {
			final JsonParser parser = new ObjectMapper().getFactory().createParser(json);
			while (parser.nextToken() != null) {
			}
			valid = true;
		} catch (JsonParseException jpe) {
			jpe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return valid;
	}

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("setUpBeforeClass() :: building test objects...");
		mockPlace1 = new Place(1L, "Dallas","Texas","USA");
		mockPlace2 = new Place(2L, "Chicago","Illinois","USA");
		
		mockPlaceCreation = new Place(3L,"Bangalore","Karnataka","India");
		
		mockPlaceModification = mockPlaceCreation;
		mockPlaceModification.setId(1L);
		mockPlaceModification.setCountry("India");
		mockPlaceModification.setCity("Chennai");
		mockPlaceModification.setState("Tamil Nadu");
		
		mockPlaceDeletion = new Place(3L, "Los Angeles","California","USA");

		dummyDb = new ArrayList<>();
		dummyDb.add(mockPlace1);
		dummyDb.add(mockPlace2);
	
	}
	@Test
	@Order(1)
	@DisplayName("1. AppContext Sanity Test")
	public void contextLoads() throws Exception {

		assertThat(placeController).isNotNull();
	}

	@Test
	@Order(2)
	@DisplayName("2. Create Place Test")
	public void testCreatePlace() throws Exception {
		// id number of this creation should be 3
		mockPlaceCreation.setId(3);
		//tell Mockito the behavior that I want this method to act like in the mock environment
		when(Pservice.createPlace(mockPlaceCreation)).thenReturn(1);
		
		//act
		RequestBuilder request = MockMvcRequestBuilders.post("/api/Place/place")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(om.writeValueAsString(mockPlaceCreation))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();
		System.out.println(result.getResponse());
		//assert
		assertEquals(om.writeValueAsString(ClientMessageUtil.CREATION_SUCCESSFUL),
				result.getResponse().getContentAsString());
	}
	 @Test
		@Order(3)
		@DisplayName("3. Delete Place Test")
		public void testDeletePlaceStatus() throws Exception {
			when(Pservice.deletePlace(mockPlaceDeletion)).thenReturn(true);
			RequestBuilder request = MockMvcRequestBuilders.delete("/api/Place/place")
					.accept(MediaType.APPLICATION_JSON_VALUE)
					.content(om.writeValueAsString(mockPlaceDeletion))
					.contentType(MediaType.APPLICATION_JSON);
			MvcResult result = mockmvc.perform(request).andReturn();
			assertEquals(om.writeValueAsString(DELETION_SUCCESSFUL),
					result.getResponse().getContentAsString());
		}
	 @Test
		@Order(4)
		@DisplayName("4. Get Place by Id")
		public void testGetPlaceById() throws Exception {
			when(Pservice.getPlaceById(mockPlace1.getId())).thenReturn((mockPlace1));
			RequestBuilder request = MockMvcRequestBuilders.get("/api/Place/place")
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.content(om.writeValueAsString(mockPlace1.getId()))
			.contentType(MediaType.APPLICATION_JSON);
			MvcResult result = mockmvc.perform(request).andReturn();
			assertEquals(om.writeValueAsString(mockPlace1), result.getResponse().getContentAsString());

	    }
	 @Test
		@Order(5)
		@DisplayName("5. Update an Existing Place")
		// @Disabled("Disabled until CreateCandyTest is up!")
		public void testUpdatePlace() throws Exception {
			when(Pservice.updatePlace(mockPlaceModification)).thenReturn(true);
			RequestBuilder request = MockMvcRequestBuilders.put("/api/Place/update")
					.accept(MediaType.APPLICATION_JSON_VALUE)
					.content(om.writeValueAsString(mockPlaceModification))
					.contentType(MediaType.APPLICATION_JSON);
			MvcResult result = mockmvc.perform(request).andReturn();
			assertEquals(om.writeValueAsString(UPDATE_SUCCESSFUL),
					result.getResponse().getContentAsString());
		}
	 @Test
		@Order(6)
		@DisplayName("6. Get All places")
		public void testGetAllPlace() throws Exception {
			when(Pservice.getAllPlaces()).thenReturn((dummyDb));
			RequestBuilder request = MockMvcRequestBuilders.get("/api/Place/allPlaces")
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.content(om.writeValueAsString(mockPlace1))
			.contentType(MediaType.APPLICATION_JSON);
			MvcResult result = mockmvc.perform(request).andReturn();
			assertEquals(om.writeValueAsString(dummyDb), result.getResponse().getContentAsString());

	    }
	 @Test
		@Order(7)
		@DisplayName("7. Find All by Location")
		public void testGetAllByLocation() throws Exception {
			when(Pservice.getAllPlacesByLocation(mockPlace1)).thenReturn((dummyDb));
			RequestBuilder request = MockMvcRequestBuilders.get("/api/Place/findAllByLocation")
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.content(om.writeValueAsString(mockPlace1))
			.contentType(MediaType.APPLICATION_JSON);
			MvcResult result = mockmvc.perform(request).andReturn();
			assertEquals(om.writeValueAsString(dummyDb), result.getResponse().getContentAsString());

	    }

}
