package com.jobly.controllers;

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
import com.jobly.bodies.NewJobListingRequest;
import com.jobly.bodies.NewUserRequest;
import com.jobly.models.Company;
import com.jobly.models.Place;
import com.jobly.models.User;
import com.jobly.models.UserRole;
import com.jobly.services.UserRoleService;
import com.jobly.services.UserService;
import com.jobly.util.ClientMessageUtil;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {
	private static NewUserRequest mockUserCreation;
	private static UserRole mockUserRole1;
	private static UserRole mockUserRole2;
	private static User mockUser1;
	private static User mockUser2;
	private static UserRole mockUserRoleCreation;
	private static User mockUserCreation1;
	private static UserRole mockUserRoleModification;
	private static User mockUserModification;
	private static UserRole mockUserRoleDeletion;
	private static User mockUserDeletion;
	private static List<UserRole> dummyDb;
	private static List<User> dummyDb1;

	ObjectMapper om = new ObjectMapper();

	@Autowired
	UserController userController;
	
    @Autowired
	private MockMvc mockmvc;


	@MockBean
	private static UserRoleService urservice;
    @MockBean
    private static UserService service;
    
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
		mockUserRole1 = new UserRole(1, "Applicant");
		mockUserRole2 = new UserRole(2, "Employer");
		mockUser1= new User(1, "jowill","joel","will","jowill@gmail.com","jowill",new UserRole(1,"Applicant"),new Company(1,"Capgemini","tech@capgemini.com",new Place(1,"Dallas","Texas","USA")));
		mockUser2= new User(2, "jowill1","joel1","will1","jowill1@gmail.com","jowill1",new UserRole(2,"Employer"),new Company(2,"Amazon","tech@amazon.com",new Place(2,"Chicago","Illinois","USA")));

		mockUserRoleCreation = new UserRole(1L,"Admin");
		mockUserCreation=new NewUserRequest(mockUser1,mockUser1.getRole().getRole(),mockUser1.getCompany().getName(),mockUser1.getCompany().getId());
		
		mockUserRoleModification = mockUserRoleCreation;
		mockUserRoleModification.setId(1L);
		mockUserRoleModification.setRole("Principal");

		mockUserModification=mockUser1;
		mockUserModification.setId(2L);
		mockUserModification.setEmail("jowill@gamil.com");
		
		mockUserRoleDeletion = new UserRole(3L, "Principal");

		dummyDb = new ArrayList<>();
		dummyDb1=new ArrayList<>();
		dummyDb.add(mockUserRole1);
		dummyDb.add(mockUserRole2);
		dummyDb1.add(mockUser1);
		dummyDb1.add(mockUser2);

	
	}
	@Test
	@Order(1)
	@DisplayName("1. AppContext Sanity Test")
	public void contextLoads() throws Exception {

		assertThat(userController).isNotNull();
	}

	@Test
	@Order(2)
	@DisplayName("2. Create UserRole Test")
	public void testCreateUserRole() throws Exception {
		// id number of this creation should be 3
		mockUserRoleCreation.setId(3);
		//tell Mockito the behavior that I want this method to act like in the mock environment
		when(urservice.createRole(mockUserRoleCreation)).thenReturn(1);
		
		//act
		RequestBuilder request = MockMvcRequestBuilders.post("/api/User/userRole")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(om.writeValueAsString(mockUserRoleCreation))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();
		System.out.println(result.getResponse());
		//assert
		assertEquals(om.writeValueAsString(ClientMessageUtil.CREATION_SUCCESSFUL),
				result.getResponse().getContentAsString());
	}
	@Test
	@Order(3)
	@DisplayName("3. Delete UserRole Test")
	public void testDeleteUserRole() throws Exception {
		when(urservice.deleteRole(mockUserRoleDeletion)).thenReturn(true);
		RequestBuilder request = MockMvcRequestBuilders.delete("/api/User/userRole")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(om.writeValueAsString(mockUserRoleDeletion))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();
		assertEquals(om.writeValueAsString(ClientMessageUtil.DELETION_SUCCESSFUL),
				result.getResponse().getContentAsString());
	}

	@Test
	@Order(4)
	@DisplayName("4. Create User Test")
	public void testCreateUser() throws Exception {
		// id number of this creation should be 3
		//mockUserCreation.setId(3);
		//tell Mockito the behavior that I want this method to act like in the mock environment
		when(service.createUser(mockUserCreation.getUser(),mockUserCreation.getRole())).thenReturn(1);
		
		//act
		RequestBuilder request = MockMvcRequestBuilders.post("/api/User/account/register")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(om.writeValueAsString(mockUserCreation))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();
		//assert
		assertEquals(om.writeValueAsString(ClientMessageUtil.CREATION_SUCCESSFUL),
				result.getResponse().getContentAsString());
	}
	@Test
	@Order(5)
	@DisplayName("5. Login User Test")
	public void testLoginUser() throws Exception {
		// id number of this creation should be 3

		//tell Mockito the behavior that I want this method to act like in the mock environment
		when(service.login(mockUser1.getUsername(),mockUser1.getPassword())).thenReturn(mockUser1);
		
		//act
		RequestBuilder request = MockMvcRequestBuilders.post("/api/User/login")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(om.writeValueAsString(mockUser1))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();
		//assert
		assertEquals(om.writeValueAsString(ClientMessageUtil.CREATION_SUCCESSFUL),
				result.getResponse().getContentAsString());
	}
    @Test
	@Order(6)
	@DisplayName("6. Update an Existing User")
	// @Disabled("Disabled until CreateCandyTest is up!")
	public void testUpdateJobListing() throws Exception {
		when(service.updateUser(mockUserModification)).thenReturn(true);
		RequestBuilder request = MockMvcRequestBuilders.put("/api/User/update")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(om.writeValueAsString(mockUserModification))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();
		assertEquals(om.writeValueAsString(UPDATE_SUCCESSFUL),
				result.getResponse().getContentAsString());
	}
    @Test
	@Order(7)
	@DisplayName("7. Get User by Email")
	public void testGetUserByEmail() throws Exception {
		when(service.getUserBYEmail(mockUser1.getEmail())).thenReturn(mockUser1);
		RequestBuilder request = MockMvcRequestBuilders.get("/api/User/findUserByEmail")
		.accept(MediaType.APPLICATION_JSON_VALUE)
		.content(om.writeValueAsString(mockUser1.getEmail()))
		.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockmvc.perform(request).andReturn();
		assertEquals(om.writeValueAsString(mockUser1), result.getResponse().getContentAsString());

    }
    
}
