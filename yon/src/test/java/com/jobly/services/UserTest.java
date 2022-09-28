package com.jobly.services;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Order;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jobly.models.Company;
import com.jobly.models.Place;
import com.jobly.models.User;
import com.jobly.models.UserRole;
import com.jobly.repositories.UserRepository;
import com.jobly.repositories.UserRoleRepository;



@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTest {
	@Mock
	private static UserRepository mockdao;
	private static UserRoleRepository mockdao1;
	
	@InjectMocks
	private static UserServiceImpl cserv;
	private static User u1,u2;
	static List<User> dummydb;
	@BeforeAll
	static void setUpBeforeClass() throws Exception{
		mockdao = Mockito.mock(UserRepository.class);
		mockdao1=Mockito.mock(UserRoleRepository.class);
		cserv = new UserServiceImpl(mockdao);
		u1 = new User(1, "jowill","joel","will","jowill@gmail.com","jowill",new UserRole(1,"Applicant"),new Company(1,"Capgemini","tech@capgemini.com",new Place(1,"Dallas","Texas","USA")));
		u2=new User(2, "jomwill","joel1","will1","jowill1@gmail.com","jomwill",new UserRole(2,"Employer"),new Company(2,"Amazon","tech@amazon.com",new Place(2,"Chicago","Illinois","USA")));
		dummydb = new ArrayList<User>();
		dummydb.add(u1);
		dummydb.add(u2);
	}
	@Test
	@Order(1)
	@DisplayName("1. Mock Validation Sanity Test")
	void checkMockInjection() {
		assertThat(mockdao).isNotNull();
		assertThat(mockdao1).isNotNull();
		assertThat(cserv).isNotNull();
	}
	@Test
	@Order(2)
	@DisplayName("2. Create User Test")
	void testCreateUser() {
		//arrange step
		User u3 = new User(3, "jomwil18l","joel18","will18","jowill18@gmail.com","jomwill18",new UserRole(3,"Admin"),new Company(3,"Infosys","tech@infosys.com",new Place(1,"Bangalore","Karnataka","India")));
		u3.setId(3);
		
		//here we will tell mockito what type of behavior to expect from calling certain methods from our dao
        when(mockdao.save(u3)).thenReturn(u3);
        
		//act + assert step
		assertEquals(1, cserv.createUser(u3,"Admin"));
	}
	@Test
	@Order(3)
	@DisplayName("3. Get User by email Test")
	void testGetUserByemail() {
		//arrange step already done in setup
		//here we will tell mockito what type of behavior to expect from calling certain methods from our dao
		when(cserv.getUserBYEmail("jowill@gmail.com")).thenReturn(u1);
        
		//act + assert step
		assertEquals(u1, cserv.getUserBYEmail("jowill@gmail.com"));
	}
	@Test
	@Order(4)
	@DisplayName("4. login Test")
	void testlogin() {
		//arrange step already done in setup
		//here we will tell mockito what type of behavior to expect from calling certain methods from our dao
		when(cserv.login("jowill","jowill")).thenReturn(u1);
        
		//act + assert step
		assertEquals(u1, cserv.login("jowill","jowill"));
	}
	
}