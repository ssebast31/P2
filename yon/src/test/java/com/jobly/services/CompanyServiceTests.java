package com.jobly.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.jobly.models.Company;
import com.jobly.models.Place;
import com.jobly.repositories.CompanyRepository;
import com.jobly.repositories.PlaceRepository;

public class CompanyServiceTests {
	
	@Mock
	private static CompanyRepository mockdao;
	
	@Mock
	private static PlaceRepository pmockdao;
	
	@InjectMocks
	private static CompanyServiceImpl cserv;
	
	private static Place p1;
	private static Company c1, c2;
	static List<Company> dummyDb;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mockdao = Mockito.mock(CompanyRepository.class);
		pmockdao = Mockito.mock(PlaceRepository.class);
		cserv = new CompanyServiceImpl(mockdao, pmockdao);

		p1 = new Place(1, "New York", "New York", "US");
		c1 = new Company(1, "Testco", "testco@mymail.com", p1);
		c2 = new Company(2, "PlaceInc", "placeinc@mymail.com", null);
		
		dummyDb = new ArrayList<Company>();
		dummyDb.add(c1);
		dummyDb.add(c2);
	}
	
	@Test
	@Order(1)
	@DisplayName("1. Mock Validation Sanity Test")
	public void checkMockInjection() {
		assertThat(mockdao).isNotNull();
		assertThat(cserv).isNotNull();
	}
	
	@Test
	@Order(2)
	@DisplayName("2. Get Company by Id")
	public void checkGetByID() {
		when(mockdao.getReferenceById((long) 1)).thenReturn(c1);
		when(mockdao.getReferenceById((long) 2)).thenReturn(c2);
		Company input = new Company();
		
		input.setId(1);
		assertEquals(c1, cserv.getCompanyById(input));
		
		input.setId(2);
		assertEquals(c2, cserv.getCompanyById(input));
	}
	
	@Test
	@Order(3)
	@DisplayName("3. Get Company by Name")
	public void checkGetByName() {
		when(mockdao.getByName("Testco")).thenReturn(c1);
		when(mockdao.getByName("PlaceInc")).thenReturn(c2);
		assertEquals(c1, cserv.getCompanyByName("Testco"));
		assertEquals(c2, cserv.getCompanyByName("PlaceInc"));
	}
	
	@Test
	@Order(4)
	@DisplayName("4. Create Company")
	public void checkCreatePlace() {
		when(mockdao.save(c1)).thenReturn(c1);
		when(pmockdao.save(p1)).thenReturn(p1);
		when(mockdao.save(c2)).thenReturn(c2);
		assertEquals(1, cserv.createCompany(c1, p1));
		assertEquals(1, cserv.createCompany(c2, null));
	}
	
	@Test
	@Order(5)
	@DisplayName("5. Update Company")
	public void checkUpdateCompany() {
		when(mockdao.save(c1)).thenReturn(c1);
		c1.setName("Testco revolved");
		c2.setEmail("nowhere");
		assertEquals(true, cserv.updateCompany(c1));
		assertEquals(true, cserv.updateCompany(c2));
	}
	
	@Test
	@Order(6)
	@DisplayName("6. Delete Company")
	public void checkDeleteCompany() {
		doNothing().when(mockdao).delete(c1);
		assertEquals(true, cserv.deleteCompany(c1));
	}
	
	@Test
	@Order(7)
	@DisplayName("7. Get All Companies")
	public void checkGetAllCompanies() {
		when(mockdao.findAll()).thenReturn(dummyDb);
		assertEquals(dummyDb, cserv.getAllCompanies());
	}
	
	@Test
	@Order(8)
	@DisplayName ("8. Get Companies By Location")
	public void checkGetCompaniesByLocation() {
		List<Company> target = new ArrayList<>();
		target.add(c1);
		when(mockdao.findAllByLocation(p1)).thenReturn(target);
		
		assertEquals(target, cserv.getAllCompaniesByLocation(p1));
	}
}
