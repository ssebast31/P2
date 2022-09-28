package com.jobly.services;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Order;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jobly.models.Application;
import com.jobly.models.ApplicationStatus;
import com.jobly.models.Company;
import com.jobly.models.JobListing;
import com.jobly.models.ListingStatus;
import com.jobly.models.Place;
import com.jobly.models.User;
import com.jobly.models.UserRole;
import com.jobly.repositories.ApplicationRepository;
import com.jobly.repositories.ApplicationStatusRepository;
import com.jobly.repositories.JobListingRepository;
import com.jobly.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApplicationServiceTest {
	@Mock
	private static ApplicationRepository mockdao;
	private static ApplicationStatusRepository mockdao1;
	private static JobListingRepository mockdao2;
	private static UserRepository mockdao3;
	
	@InjectMocks
	private static ApplicationServiceImpl cserv;
	private static Application u1,u2;
	private static JobListing a1;
	private static User b1;
	static List<Application> dummydb;
	@BeforeAll
	static void setUpBeforeClass() throws Exception{
		mockdao = Mockito.mock(ApplicationRepository.class);
		mockdao1=Mockito.mock(ApplicationStatusRepository.class);
		mockdao2=Mockito.mock(JobListingRepository.class);
		mockdao3=Mockito.mock(UserRepository.class);
		cserv = new ApplicationServiceImpl(mockdao,mockdao1,mockdao2,mockdao3);
		u1 = new Application(1L, new User(1L, "jowill","joel","will","jowill@gmail.com","jowill",new UserRole(1,"Applicant"),new Company(1L,"Capgemini","tech@capgemini.com",new Place(1,"Dallas","Texas","USA"))),new byte[] {1,2,3} , new JobListing(1L,"Engineer", new Place(1L,"Chicago","Illinois","USA"), new Company(1L,"Capgemini","tech@capgemini.com",new Place(1L,"Chicago","Illinois","USA")), new ListingStatus(1L,"Pending")),new ApplicationStatus(1L,"Pending"));
		u2=new Application(2L, new User(2L, "jowill1","joel1","will1","jowill1@gmail.com","jowill1",new UserRole(2,"Employer"),new Company(2L,"Amazon","tech@amazon.com",new Place(1,"Chicago","Illinois","USA"))),new byte[] {3,4,5} , new JobListing(2L,"BBA", new Place(2L,"Chicago","Illinois","USA"), new Company(2L,"Amazon","tech@amazon.com",new Place(2L,"Chicago","Illinois","USA")), new ListingStatus(2L,"Accepting")),new ApplicationStatus(2L,"Accepted"));
		a1=new JobListing(2L,"BBA", new Place(2L,"Chicago","Illinois","USA"), new Company(2L,"Amazon","tech@amazon.com",new Place(2L,"Chicago","Illinois","USA")), new ListingStatus(1L,"Pending"));
		b1= new User(1L, "jowill","joel","will","jowill@gmail.com","jowill",new UserRole(1,"Applicant"),new Company(1L,"Capgemini","tech@capgemini.com",new Place(1,"Dallas","Texas","USA")));
		dummydb = new ArrayList<Application>();
		dummydb.add(u1);
		dummydb.add(u2);
	}
	@Test
	@Order(1)
	@DisplayName("1. Mock Validation Sanity Test")
	void checkMockInjection() {
		assertThat(mockdao).isNotNull();
		assertThat(cserv).isNotNull();
		assertThat(mockdao1).isNotNull();
		assertThat(mockdao2).isNotNull();
		assertThat(mockdao3).isNotNull();
	}
	@Test
	@Order(2)
	@DisplayName("2. Create Application Test")
	void testCreateApplication() {
		//arrange step
		Application u3 = new Application(3L, new User(3, "jowill18","joel18","will18","jowill18@gmail.com","jowill18",new UserRole(3,"Admin"),new Company(2,"Amazon","tech@amazon.com",new Place(1,"Chicago","Illinois","USA"))),new byte[] {3,4,5} , new JobListing(1,"BBA", new Place(1,"Chicago","Illinois","USA"), new Company(1,"Amazon","tech@amazon.com",new Place(1,"Chicago","Illinois","USA")), new ListingStatus(2,"Accepting")),new ApplicationStatus(1,"Accepted"));
		u3.setId(3L);
		
		//here we will tell mockito what type of behavior to expect from calling certain methods from our dao
        when(mockdao.save(u3)).thenReturn(u3);
        
		//act + assert step
		assertEquals(1, cserv.createApplication(u3,new User(3, "jomwil18l","joel18","will18","jowill18@gmail.com","jomwill18",new UserRole(3,"Admin"),new Company(3L,"Infosys","tech@infosys.com",new Place(1L,"Bangalore","Karnataka","India"))),3L));
	}
	@Test
	@Order(3)
	@DisplayName("3. Delete Application Test")
	void testDeleteUserRole() {
        doNothing().when(mockdao).delete(u2);
		//act + assert step
		assertEquals(true, cserv.deleteApplication(u2));
	}
	@Test
	@Order(4)
	@DisplayName("4. Get all Application by job listing Test")
	void testGetAllApplicant() {
		//arrange step already done in setup
		//here we will tell mockito what type of behavior to expect from calling certain methods from our dao
		when(cserv.getApplicationByListing(a1)).thenReturn(dummydb);
        
		//act + assert step
		assertEquals(dummydb, cserv.getApplicationByListing(a1));
	}
	@Test
	@Order(5)
	@DisplayName("5. Get Application by ID Test")
	void testGetapplicationbyId() {
		//arrange step already done in setup
		//here we will tell mockito what type of behavior to expect from calling certain methods from our dao
		when(cserv.getApplicationById(1)).thenReturn(u1);
        
		//act + assert step
		assertEquals(u1, cserv.getApplicationById(1));
	}
	@Test
	@Order(6)
	@DisplayName("6. Get all Application by User Test")
	void testGetApplicationByUser() {
		//arrange step already done in setup
		//here we will tell mockito what type of behavior to expect from calling certain methods from our dao
		when(cserv.getApplicationByUser(b1)).thenReturn(dummydb);
        
		//act + assert step
		assertEquals(dummydb, cserv.getApplicationByUser(b1));
	}
	@Test
	@Order(7)
	@DisplayName("7. Update Application Status Test")
	void testUpdateApplication() {
		u2.setId(2L);
		u2.setStatus(new ApplicationStatus(2L,"Rejected"));
		
		when(cserv.getApplicationById(2)).thenReturn(u2);
		when(mockdao.save(u2)).thenReturn(u2);
		
		assertEquals(true, cserv.updateCompany(u2));
	}

}