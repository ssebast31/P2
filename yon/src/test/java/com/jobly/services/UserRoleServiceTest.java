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

import com.jobly.models.UserRole;
import com.jobly.repositories.UserRoleRepository;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class UserRoleServiceTest {
	@Mock
	private static UserRoleRepository mockdao;
	
	@InjectMocks
	private static UserRoleServiceImpl cserv;
	private static UserRole u1,u2;
	static List<UserRole> dummydb;
	@BeforeAll
	static void setUpBeforeClass() throws Exception{
		mockdao = Mockito.mock(UserRoleRepository.class);
		cserv = new UserRoleServiceImpl(mockdao);
		u1 = new UserRole(1, "Applicant");
		u2=new UserRole(2,"Employeer");
		dummydb = new ArrayList<UserRole>();
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
	@DisplayName("2. Create UserRole Test")
	void testCreateUserRole() {
		//arrange step
		UserRole u3 = new UserRole(3,"Admin");
		u3.setId(3);
		
		//here we will tell mockito what type of behavior to expect from calling certain methods from our dao
        when(mockdao.save(u3)).thenReturn(u3);
        
		//act + assert step
		assertEquals(1, cserv.createRole(u3));
	}
	@Test
	@Order(3)
	@DisplayName("3. Get UserRole by ID Test")
	void testGetUserRoleById() {
		//arrange step already done in setup
		//here we will tell mockito what type of behavior to expect from calling certain methods from our dao
		when(cserv.getRole(1)).thenReturn(u1);
        
		//act + assert step
		assertEquals(u1, cserv.getRole(1));
	}
	@Test
	@Order(4)
	@DisplayName("4. Get all UserRole Test")
	void testGetAllUserRoles() {
		//arrange step already done in setup
		//here we will tell mockito what type of behavior to expect from calling certain methods from our dao
		when(cserv.getAllUserRoles()).thenReturn(dummydb);
        
		//act + assert step
		assertEquals(dummydb, cserv.getAllUserRoles());
	}
	@Test
	@Order(5)
	@DisplayName("5. Update UserRole Test")
	void testUpdateUserRole() {
		u2.setId(2);
		u2.setRole("Principal");
		
		when(cserv.getRole(2)).thenReturn(u2);
		when(mockdao.save(u2)).thenReturn(u2);
		
		assertEquals(true, cserv.updateUserRoles(u2));
	}
	@Test
	@Order(6)
	@DisplayName("6. Delete UserRole Test")
	void testDeleteUserRole() {
        doNothing().when(mockdao).delete(u2);
		//act + assert step
		assertEquals(true, cserv.deleteRole(u2));
	}


	
}