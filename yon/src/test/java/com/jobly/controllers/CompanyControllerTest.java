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
import com.jobly.bodies.NewCompanyRequest;
import com.jobly.models.Company;
import com.jobly.models.Place;
import com.jobly.services.CompanyService;
import static com.jobly.util.ClientMessageUtil.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CompanyController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CompanyControllerTest {
	private static NewCompanyRequest mockCompanyCreation;
    private static Company mockCompany1;
    private static Company mockCompany2;
    private static Company mockCompanyModification;
    private static Company mockCompanyDeletion;

    private static Place place1;
    private static List<Company> dummyDb;

    ObjectMapper om = new ObjectMapper();

    @Autowired
    CompanyController companyController;

    @Autowired
    private MockMvc mockmvc;
    
    @MockBean
    private CompanyService cserv;

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
        
        mockCompany1 = new Company(1L, "Testco", "testco@mymail.com", place1);
		mockCompany2 = new Company(2L, "PlaceInc", "placeinc@mymail.com", null);
        
 
         //dummy DB setup
         
         mockCompanyCreation = new  NewCompanyRequest(mockCompany2,place1);
	
		
		mockCompanyModification = new Company(3L,"name3", "email", place1);
		mockCompanyModification.setName("newName");
		mockCompanyModification.setEmail("newEmail");
		
		mockCompanyDeletion =new Company(4L, "nam4", "company4", place1);
		
		dummyDb = new ArrayList<Company>();
		dummyDb.add(mockCompany1);
		dummyDb.add(mockCompany2);
	}

    @Test
	@Order(1)
	@DisplayName("1. AppContext Sanity Test")
	public void contextLoads() throws Exception {
		assertThat(companyController).isNotNull();
	}
    @Test
	@Order(2)
	@DisplayName("2. Create Company - Happy Path Scenerio Test")
	public void testCreateCompany() throws Exception {

		
		// // id number of this creation should be 3
		
		//tell Mockito the behavior that I want this method to act like in the mock environment
		when(cserv.createCompany(mockCompanyCreation.getCompany(),mockCompanyCreation.getPlace())).thenReturn(1);
		//act
		RequestBuilder request = MockMvcRequestBuilders.post("/api/Company/company")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(om.writeValueAsString(mockCompanyCreation))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();
		//assert
		assertEquals(om.writeValueAsString(CREATION_SUCCESSFUL),
				result.getResponse().getContentAsString());
	}

	@Test
	@Order(3)
	@DisplayName("3. Get all Companies by Location - Happy Path Scenerio Test")
	public void testGetCompanyByLocation() throws Exception {
		when(cserv.getAllCompaniesByLocation(place1)).thenReturn(dummyDb);
		RequestBuilder request = MockMvcRequestBuilders.get("/api/Company/findAllByLocation")
		.accept(MediaType.APPLICATION_JSON_VALUE)
		.content(om.writeValueAsString(mockCompany1.getPlace()))
		.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();
		assertEquals(om.writeValueAsString(dummyDb), result.getResponse().getContentAsString());

    }
	@Test
	@Order(4)
	@DisplayName("4. Get all Companies by Name - Happy Path Scenerio Test")
	public void testGetCompanyByName() throws Exception {
		when(cserv.getCompanyByName("Testco")).thenReturn((mockCompany1));
		RequestBuilder request = MockMvcRequestBuilders.get("/api/Company/company")
		.accept(MediaType.APPLICATION_JSON_VALUE)
		.content(om.writeValueAsString(mockCompany1))
		.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();
		assertEquals(om.writeValueAsString(mockCompany1), result.getResponse().getContentAsString());

    }

	@Test
	@Order(5)
	@DisplayName("5. Update an Existing company - Happy Path Scenerio Test")
	public void testUpdateCompany() throws Exception {
		when(cserv.updateCompany(mockCompanyModification)).thenReturn(true);
		RequestBuilder request = MockMvcRequestBuilders.put("/api/Company/update")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(om.writeValueAsString(mockCompanyModification))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();
		assertEquals(om.writeValueAsString(UPDATE_SUCCESSFUL),
				result.getResponse().getContentAsString());
	}

	
	@Test
	@Order(6)
	@DisplayName("6. Delete Company - Happy Path Scenerio Test")
	public void testDeleteCompany() throws Exception {
		when(cserv.deleteCompany(mockCompanyDeletion)).thenReturn(true);
		RequestBuilder request = MockMvcRequestBuilders.delete("/api/Company/company")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(om.writeValueAsString(mockCompanyDeletion))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();
		assertEquals(om.writeValueAsString(DELETION_SUCCESSFUL),
				result.getResponse().getContentAsString());
	}

	@Test
	@Order(7)
	@DisplayName("7. Unneccessay/Unused Test")
	@Disabled("Disabled until CreateApplicationTest is up!") 
	public void unusedTest() {
		return;
	}
	





    
    

    
}
