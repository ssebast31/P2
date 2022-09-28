package com.jobly.controllers;

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
import com.jobly.bodies.NewJobListingRequest;
import com.jobly.models.Company;
import com.jobly.models.JobListing;
import com.jobly.models.ListingStatus;
import com.jobly.models.Place;
import com.jobly.services.JobListingService;
import com.jobly.services.ListingStatusService;
import static com.jobly.util.ClientMessageUtil.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(JobListingController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JobListingControllerTest {
	private static NewJobListingRequest mockJobListingCreation;
    private static JobListing mockJobListing1;
	private static JobListing mockJobListing2;
	private static JobListing mockJobListing3;
	private static JobListing mockJobListingModification;
	private static JobListing mockJobListingDeletion;

    private static Place  place1,place2;
    private static Company company1, company2;
    private static ListingStatus listingStatus1,listingStatus2;
	private static List<JobListing> dummyDb;

	ObjectMapper om = new ObjectMapper();
	
	@Autowired
	JobListingController jobListingController;
	
	@Autowired
	private MockMvc mockmvc;
	
	@MockBean
	private JobListingService jlserv;
    @MockBean 
    private ListingStatusService lService;
	
	@SuppressWarnings("deprecation")
	public boolean isValidJSON (final String json) {
		boolean valid = false;
		try {
			final JsonParser parser = new ObjectMapper().getJsonFactory().createJsonParser(json);
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
		
        //make objects required for job listing 
         place1 = new Place(1L, "Phoenix", "AZ", "US");
         company1 = new Company(1L, "Amazon", "email@email.com", place1);
         listingStatus1 = new ListingStatus(1, "active");
 
         place2 = new Place(2L, "San Franciso", "CAL", "US");
         company2 = new Company(2L, "Apple", "email2@email.com", place1);
         listingStatus2 = new ListingStatus(2, "closed");
 
 
         //dummy DB setup
         mockJobListing1 = new JobListing(1L, "description", place1, company1, listingStatus1);
         mockJobListing2 = new JobListing(2L, "description", place2, company2, listingStatus1);
		 mockJobListing3= new JobListing(3L, "description3", place1,company2,listingStatus2);
       
		
		 mockJobListingCreation= new NewJobListingRequest(mockJobListing3, mockJobListing3.getPlace(),mockJobListing3.getCompany());
		 mockJobListingModification= mockJobListing3;
		mockJobListingModification.setDescription("new desc");
		mockJobListingModification.setStatus(new ListingStatus(3L, "pending"));
		
		mockJobListingDeletion = new JobListing(4L, "description4", new Place(4L, "Ciy4", "State4", "country4"), new Company(4L, "name4", "email4",  new Place(4L, "Ciy4", "State4", "country4")), new ListingStatus(3L, "pending"));

		dummyDb = new ArrayList<>();
		dummyDb.add(mockJobListing1);
		dummyDb.add(mockJobListing2);
	}

	/*
	 * Sanity Check - if this fails, application context is not loaded and all other
	 * tests should fail
	 */
	@Test
	@Order(1)
	@DisplayName("1. AppContext Sanity Test")
	public void contextLoads() throws Exception {
		assertThat(jobListingController).isNotNull();
	}
    @Test
	@Order(2)
	@DisplayName("2. Create Job Listing - Happy Path Scenerio Test")
	public void testCreateJobListing() throws Exception {
		// // id number of this creation should be 3
		
		//tell Mockito the behavior that I want this method to act like in the mock environment
		when(jlserv.createListing(mockJobListingCreation.getListing(),mockJobListingCreation.getPlace(),mockJobListingCreation.getCompany())).thenReturn(true);
		//act
		RequestBuilder request = MockMvcRequestBuilders.post("/api/jobListing/listing")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(om.writeValueAsString(mockJobListingCreation))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();
		//assert
		assertEquals(om.writeValueAsString(CREATION_SUCCESSFUL),
				result.getResponse().getContentAsString());
	}

    @Test
	@Order(3)
	@DisplayName("3. Get all JobListing - Happy Path Scenerio Test")
	public void testGetJoblistingByLocation() throws Exception {
		when(jlserv.getAllJobListingByLocation(place2)).thenReturn((dummyDb));
		RequestBuilder request = MockMvcRequestBuilders.get("/api/jobListing/findAllByLocation")
		.accept(MediaType.APPLICATION_JSON_VALUE)
		.content(om.writeValueAsString(mockJobListing2.getPlace()))
		.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();
		assertEquals(om.writeValueAsString(dummyDb), result.getResponse().getContentAsString());

    }

    @Test
	@Order(4)
	@DisplayName("4. Update an Existing JobLisitng - Happy Path Scenerio Test")
	public void testUpdateJobListing() throws Exception {
		when(jlserv.updateJobListing(mockJobListingModification)).thenReturn(true);
		RequestBuilder request = MockMvcRequestBuilders.put("/api/jobListing/update")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(om.writeValueAsString(mockJobListingModification))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();
		assertEquals(om.writeValueAsString(UPDATE_SUCCESSFUL),
				result.getResponse().getContentAsString());
	}
    @Test
	@Order(5)
	@DisplayName("5. Delete JobLisitng - Happy Path Scenerio Test")
	public void testDeleteJobListing() throws Exception {
		when(jlserv.deleteListing(mockJobListingDeletion)).thenReturn(true);
		RequestBuilder request = MockMvcRequestBuilders.delete("/api/jobListing/Listing")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(om.writeValueAsString(mockJobListingDeletion))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();
		assertEquals(om.writeValueAsString(DELETION_SUCCESSFUL),
				result.getResponse().getContentAsString());
	}
    @Test
	@Order(6)
	@DisplayName("6. Create ListingStatus - Happy Path Scenerio Test")
	public void testCreateJobListingStatus() throws Exception {
		// // id number of this creation should be 3
		
		//tell Mockito the behavior that I want this method to act like in the mock environment
		when(lService.createStatus(mockJobListingCreation.getListing().getStatus())).thenReturn(1);
		//act
		RequestBuilder request = MockMvcRequestBuilders.post("/api/jobListing/ListingStatus")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(om.writeValueAsString(mockJobListingCreation.getListing().getStatus()))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();
		//assert
		assertEquals(om.writeValueAsString(CREATION_SUCCESSFUL),
				result.getResponse().getContentAsString());
	}

    @Test
	@Order(7)
	@DisplayName("7. Delete JobLisitngStatus - Happy Path Scenerio Test")
	public void testDeleteJobListingStatus() throws Exception {
		when(lService.deleteStatus(mockJobListingDeletion.getStatus())).thenReturn(true);
		RequestBuilder request = MockMvcRequestBuilders.delete("/api/jobListing/ListingStatus")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(om.writeValueAsString(mockJobListingDeletion.getStatus()))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();
		assertEquals(om.writeValueAsString(DELETION_SUCCESSFUL),
				result.getResponse().getContentAsString());
	}

    @Test
	@Order(8)
	@DisplayName("8. Unneccessay/Unused Test")
	@Disabled("Disabled until CreateJobListingTest is up!") 
	public void unusedTest() {
		return;
	}
	

    
}
