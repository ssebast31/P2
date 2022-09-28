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

import com.jobly.models.ApplicationStatus;
import com.jobly.repositories.ApplicationStatusRepository;
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApplicationStatusServiceTest {
	@Mock
	private static ApplicationStatusRepository mockdao;
	
	@InjectMocks
	private static ApplicationStatusServiceImpl cserv;
	private static ApplicationStatus u1,u2;
	static List<ApplicationStatus> dummydb;
	@BeforeAll
	static void setUpBeforeClass() throws Exception{
		mockdao = Mockito.mock(ApplicationStatusRepository.class);
		cserv = new ApplicationStatusServiceImpl(mockdao);
		u1 = new ApplicationStatus(1, "Pending");
		u2=new ApplicationStatus(2,"Accepted");
		dummydb = new ArrayList<ApplicationStatus>();
		dummydb.add(u1);
		dummydb.add(u2);
	}
	@Test
	@Order(1)
	@DisplayName("1. Mock Validation Sanity Test")
	void checkMockInjection() {
		assertThat(mockdao).isNotNull();
		assertThat(cserv).isNotNull();
	}
	@Test
	@Order(2)
	@DisplayName("2. Create Application Status Test")
	void testCreateUserRole() {
		//arrange step
		ApplicationStatus u3 = new ApplicationStatus(3,"Denied");
		u3.setId(3);
		
		//here we will tell mockito what type of behavior to expect from calling certain methods from our dao
        when(mockdao.save(u3)).thenReturn(u3);
        
		//act + assert step
		assertEquals(1, cserv.createStatus(u3));
	}
	@Test
	@Order(3)
	@DisplayName("3. Delete Appliaction Status Test")
	void testDeleteUserRole() {
        doNothing().when(mockdao).delete(u2);
		//act + assert step
		assertEquals(true, cserv.deleteStatus(u2));
	}
	@Test
	@Order(4)
	@DisplayName("4. Update Application Status Test")
	void testUpdateUserRole() {
		u2.setId(2);
		u2.setStatus("Reviewing");
		
		when(cserv.getStatus(2)).thenReturn(u2);
		when(mockdao.save(u2)).thenReturn(u2);
		
		assertEquals(true, cserv.updateListingStatus(u2));
	}
	@Test
	@Order(5)
	@DisplayName("5. Get all Application Status Test")
	void testGetAllCandies() {
		//arrange step already done in setup
		//here we will tell mockito what type of behavior to expect from calling certain methods from our dao
		when(cserv.getAllApplicationStatus()).thenReturn(dummydb);
        
		//act + assert step
		assertEquals(dummydb, cserv.getAllApplicationStatus());
	}

}