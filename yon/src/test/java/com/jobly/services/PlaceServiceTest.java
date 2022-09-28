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
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jobly.models.Place;
import com.jobly.repositories.PlaceRepository;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class PlaceServiceTest {
	@Mock
	private static PlaceRepository mockdao;
	
	@InjectMocks
	private static PlaceServiceImpl pserv;
	
	private static Place p1, p2, p3;
	static List<Place> dummyDb;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mockdao = Mockito.mock(PlaceRepository.class);
		pserv = new PlaceServiceImpl(mockdao);
		
		p1 = new Place(1, "New York", "New York", "US");
		p2 = new Place(2, "Villetown", "Colorado", "US");
		p3 = new Place(3, "Dubai", "", "UAE");
		
		dummyDb = new ArrayList<Place>();
		dummyDb.add(p1);
		dummyDb.add(p2);
		dummyDb.add(p3);
	}
	
	@Test
	@Order(1)
	@DisplayName("1. Mock Validation Sanity Test")
	public void checkMockInjection() {
		assertThat(mockdao).isNotNull();
		assertThat(pserv).isNotNull();
	}
	
	@Test
	@Order(2)
	@DisplayName("2. Get Place By Id")
	public void checkGetById() {
		when(mockdao.getReferenceById((long) 2)).thenReturn(p2);
		
		assertEquals(p2, pserv.getPlaceById(2));
	}
	
	@Test
	@Order(3)
	@DisplayName("3. Create Place")
	public void checkCreatePlace() {
		when(mockdao.save(p1)).thenReturn(p1);
		when(mockdao.save(p3)).thenReturn(p3);
		assertEquals(1, pserv.createPlace(p1));
		assertEquals(1, pserv.createPlace(p3));
	}
	
	@Test
	@Order(4)
	@DisplayName("4. Delete Place")
	public void checkDeletePlace() {
		doNothing().when(mockdao).delete(p2);
		assertEquals(true, pserv.deletePlace(p2));
	}
	
	@Test
	@Order(5)
	@DisplayName("5. Get All Places")
	public void checkGetAllPlaces() {
		when(mockdao.findAll()).thenReturn(dummyDb);
		assertEquals(dummyDb, pserv.getAllPlaces());
	}
}
