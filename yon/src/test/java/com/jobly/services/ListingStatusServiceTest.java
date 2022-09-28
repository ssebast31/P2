package com.jobly.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jobly.models.JobListing;
import com.jobly.models.ListingStatus;
import com.jobly.repositories.ListingStatusRepository;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListingStatusServiceTest {
    
    @Mock 
    private static ListingStatusRepository mockDaoListingStatus;
    
    @InjectMocks
    private static ListingStatusServiceImpl listingStatusServ;

    private static ListingStatus listingStatus1, listingStatus2;
    
    static List<ListingStatus> dummyDB;

    @BeforeAll
    static void setUpBeforeClass() throws Exception{
        
        //mockito setup
        mockDaoListingStatus = Mockito.mock(ListingStatusRepository.class);
    
        //inject service with mocked class 
        listingStatusServ = new ListingStatusServiceImpl(mockDaoListingStatus);

        //dummy DB setup
        listingStatus1 = new ListingStatus(1L, "Active");
        listingStatus2 = new ListingStatus(2L, "Inactive");
          
        dummyDB = new ArrayList<ListingStatus>();

        dummyDB.add(listingStatus1);
        dummyDB.add(listingStatus2);


    }

    @Test
    @Order(1)
    @DisplayName("1. Mock validation sanity test")
    void checkMockInjection() {
		assertThat(mockDaoListingStatus).isNotNull();
		assertThat(listingStatusServ).isNotNull();
    }

    @Test
    @Order(2)
    @DisplayName("2. Create listing status Test")
    void testCreateListingStatus(){
        //arrange setup
        ListingStatus listingStatus3 = new ListingStatus("Pending");

        listingStatus3.setId(3L);

        //here we will tell mockito what type of behavior to expect from calling certain methods from our dao
        when(mockDaoListingStatus.save(listingStatus3)).thenReturn(listingStatus3);

        //act + assert step        
        assertEquals(1,listingStatusServ.createStatus(listingStatus3));
        



    }
    @Test
    @Order(3)
    @DisplayName("3. Create listing status Test")
    void testCreateListingStatusFailure(){
        //arrange setup
        ListingStatus listingStatus3 = new ListingStatus("Active");

        listingStatus3.setId(3L);

        //here we will tell mockito what type of behavior to expect from calling certain methods from our dao
        when(mockDaoListingStatus.save(listingStatus3)).thenReturn(listingStatus3); //thenReturn(listingStatus3);

        //act + assert step        
        assertEquals(1,listingStatusServ.createStatus(listingStatus3));
        



    }

    @Test
	@Order(4)
	@DisplayName("4. Get all lisitng status")
	void testGetAllLisitngStatus() {

        when(listingStatusServ.getAllListingStatus()).thenReturn(dummyDB);
        assertEquals(dummyDB, listingStatusServ.getAllListingStatus());

    }

    @Test
	@Order(5)
	@DisplayName("5. Update listing status")
	void testUpdateJobListing() {

        listingStatus2.setStatus("cancelled");

        when(listingStatusServ.getStatusById(2L)).thenReturn(listingStatus2);
		when(mockDaoListingStatus.save(listingStatus2)).thenReturn(listingStatus2);
		
		assertEquals(true, listingStatusServ.updateListingStatus(listingStatus2));

    }

    @Test
	@Order(6)
	@DisplayName("6. Delete listing status")
	void testDeleteJobListing() {
        doNothing().when(mockDaoListingStatus).delete(listingStatus1);
		//act + assert step
		assertEquals(true, listingStatusServ.deleteStatus(listingStatus1));
        
	}



    
}
