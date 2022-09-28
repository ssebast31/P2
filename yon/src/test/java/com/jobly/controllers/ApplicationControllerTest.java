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
import com.jobly.bodies.NewApplicationRequest;
import com.jobly.models.Application;
import com.jobly.models.ApplicationStatus;
import com.jobly.models.Company;
import com.jobly.models.JobListing;
import com.jobly.models.ListingStatus;
import com.jobly.models.Place;
import com.jobly.models.User;
import com.jobly.models.UserRole;
import com.jobly.services.ApplicationService;
import com.jobly.services.ApplicationStatusService;
import static com.jobly.util.ClientMessageUtil.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ApplicationController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApplicationControllerTest {
	private static Application mockApp1;
	private static Application mockApp2;
	private static Application mockApp3;
	private static NewApplicationRequest mockAppCreation;
	private static Application mockAppModification;
	private static Application mockAppDeletion;

	private static List<Application> dummyDb;
	

	ObjectMapper om = new ObjectMapper();

	@Autowired
	ApplicationController appController;

	@Autowired
	private MockMvc mockmvc;

	@MockBean
	private ApplicationService appserv;
	@MockBean
	private ApplicationStatusService appStatusServ;

	@SuppressWarnings("deprecation")
	public boolean isValidJSON(final String json) {
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
		
		UserRole role1 = new UserRole(1,"Applicant");
		UserRole role2 = new UserRole(2,"Employer");
		
		ListingStatus listStatus1 = new ListingStatus("Open");
		ListingStatus listStatus2 = new ListingStatus("Closed");
		
		ApplicationStatus appStatus1 = new ApplicationStatus(1L,"Pending");
		ApplicationStatus appStatus2 = new ApplicationStatus(2L,"Accepted");
		
		Place place1 = new Place(1L,"Chicago","Illinois","USA");
		
		Company company1 = new Company(1L,"Capgemini","tech@capgemini.com", place1);
		Company company2 = new Company(2L,"Amazon","tech@amazon.com", place1);
		
		JobListing listing1 = new JobListing(1L,"Engineer", place1, company1, listStatus2);
		JobListing listing2 = new JobListing(2L,"BBA", place1, company2, listStatus1);
		
		User user1 = new User(1L, "jowill", "joel", "will", "jowill@gmail.com", "jowill", role1, null);
		User user2 = new User(2L, "jowill1", "joel1", "will1", "jowill1@gmail.com", "jowill1", role2, null);
		User user3 = new User(3L, "user3","user3","user3","user3","user3", role2, null);
		User user4 = new User(4L, "user4","user4","user4","user4","user4", role2, null);
		User user5 = new User(5L, "user3","user3","user3","user3","user3", role1, null);
        //dummy DB setup
        mockApp1 = new Application(1L, user1, new byte[] {1,2,3} , listing1, appStatus1);
        mockApp2 = new Application(2L, user2, new byte[] {3,4,5} , listing2, appStatus2);
        mockApp3 = new Application(3L, null, new byte[] {3,4,5} , null, appStatus2);
		
        mockAppCreation = new NewApplicationRequest(mockApp3, user3, listing2.getId());
		
		mockAppModification = new Application(3L, user3, new byte[] {3,4,5} , listing2, appStatus2);
		mockAppModification.setApplicant(user5);
		
		mockAppDeletion = new Application(4L, user4, new byte[] {3,4,5} , new JobListing(2L,"BBA", new Place(2L,"Chicago","Illinois","USA"), new Company(2L,"Amazon","tech@amazon.com",new Place(2L,"Chicago","Illinois","USA")), new ListingStatus(2L,"Accepting")),new ApplicationStatus(2L,"Accepted"));

		dummyDb = new ArrayList<>();
		dummyDb.add(mockApp1);
		dummyDb.add(mockApp2);
	}

	@Test
	@Order(1)
	@DisplayName("1. AppContext Sanity Test")
	public void contextLoads() throws Exception {
		assertThat(appController).isNotNull();
	}

	@Test
	@Order(2)
	@DisplayName("2. Create Application - Happy Path Scenerio Test")
	public void testCreateApp() throws Exception {
		// tell Mockito the behavior that I want this method to act like in the mock
		// environment
		when(appserv.createApplication(mockAppCreation.getApplication(), mockAppCreation.getUser(),
				mockAppCreation.getListingId())).thenReturn(1);
		// act
		RequestBuilder request = MockMvcRequestBuilders.post("/api/Application/application")
				.accept(MediaType.APPLICATION_JSON_VALUE).content(om.writeValueAsString(mockAppCreation))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();
		// assert
		assertEquals(om.writeValueAsString(CREATION_SUCCESSFUL), result.getResponse().getContentAsString());
	}

	@Test
	@Order(3)
	@DisplayName("3. Get all Applications by UserId - Happy Path Scenerio Test")
	public void testAppsByUser() throws Exception {
		when(appserv.getApplicationByUser(mockApp1.getApplicant())).thenReturn((dummyDb));

		RequestBuilder request = MockMvcRequestBuilders.get("/api/Application/applicant/viewAll")
				.accept(MediaType.APPLICATION_JSON_VALUE).content(om.writeValueAsString(mockApp1.getApplicant()))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();
		assertEquals(om.writeValueAsString(dummyDb), result.getResponse().getContentAsString());

	}

	@Test
	@Order(4)
	@DisplayName("4. Get all Applications by listing - Happy Path Scenerio Test")
	public void testAppsByListing() throws Exception {
		when(appserv.getApplicationByListing(mockApp1.getListing())).thenReturn((dummyDb));

		RequestBuilder request = MockMvcRequestBuilders.get("/api/Application/employer/viewAll")
				.accept(MediaType.APPLICATION_JSON_VALUE).content(om.writeValueAsString(mockApp1.getListing()))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();

		assertEquals(om.writeValueAsString(dummyDb), result.getResponse().getContentAsString());

	}

	@Test
	@Order(5)
	@DisplayName("4. Get Application by id - Happy Path Scenerio Test")
	public void testGetAppById() throws Exception {
		when(appserv.getApplicationById((int) mockApp1.getId())).thenReturn((mockApp1));

		RequestBuilder request = MockMvcRequestBuilders.get("/api/Application/findByID")
				.accept(MediaType.APPLICATION_JSON_VALUE).content(om.writeValueAsString(mockApp1))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();

		assertEquals(om.writeValueAsString(mockApp1), result.getResponse().getContentAsString());
	}

	@Test
	@Order(6)
	@DisplayName("6. Update an Existing Aplication - Happy Path Scenerio Test")
	// @Disabled("Disabled until CreateCandyTest is up!")
	public void testUpdateApp() throws Exception {
		when(appserv.updateCompany(mockAppModification)).thenReturn(true);

		RequestBuilder request = MockMvcRequestBuilders.put("/api/Application/update")
				.accept(MediaType.APPLICATION_JSON_VALUE).content(om.writeValueAsString(mockAppModification))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();

		assertEquals(om.writeValueAsString(UPDATE_SUCCESSFUL), result.getResponse().getContentAsString());
	}

	@Test
	@Order(7)
	@DisplayName("7. Delete Application - Happy Path Scenerio Test")
	public void testDeleteApp() throws Exception {
		when(appserv.deleteApplication(mockAppDeletion)).thenReturn(true);

		RequestBuilder request = MockMvcRequestBuilders.delete("/api/Application/application")
				.accept(MediaType.APPLICATION_JSON_VALUE).content(om.writeValueAsString(mockAppDeletion))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();

		assertEquals(om.writeValueAsString(DELETION_SUCCESSFUL), result.getResponse().getContentAsString());
	}

	@Test
	@Order(8)
	@DisplayName("8. Unneccessay/Unused Test")
	@Disabled("Disabled until CreateApplicationTest is up!") 
	public void unusedTest() {
		return;
	}

}
