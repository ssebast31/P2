package com.jobly.services;




import org.junit.jupiter.api.Order;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

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

import com.jobly.models.Company;
import com.jobly.models.JobListing;
import com.jobly.models.ListingStatus;
import com.jobly.models.Place;
import com.jobly.repositories.CompanyRepository;
import com.jobly.repositories.JobListingRepository;
import com.jobly.repositories.ListingStatusRepository;
import com.jobly.repositories.PlaceRepository;
import com.jobly.services.JobListingServiceImpl;



@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JobListingServiceTest {
    @Mock
    private static JobListingRepository mockDaoJobListing;
    private static PlaceRepository mockDaoPlace;
    private static CompanyRepository mockDAOCompany;
    private static ListingStatusRepository mockDAOListingStatus;
    @InjectMocks
    private static JobListingServiceImpl jobListingServ;
    private static PlaceServiceImpl placeServ;
    private static CompanyServiceImpl companyServ;
    private static ListingStatusServiceImpl listingStatusServ;


    private static JobListing jobListing1,jobListing2;
    private static Place  place1,place2;
    private static Company company1, company2;
    private static ListingStatus listingStatus1,listingStatus2;
    
    static List<JobListing> dummyDB;

    @BeforeAll
    static void setUpBeforeClass() throws Exception{

        //mockito setup
        mockDaoJobListing = Mockito.mock(JobListingRepository.class);
        mockDaoPlace = Mockito.mock(PlaceRepository.class);
        mockDAOCompany= Mockito.mock(CompanyRepository.class);
        mockDAOListingStatus = Mockito.mock(ListingStatusRepository.class);
        //inject service with mocked class
        jobListingServ = new JobListingServiceImpl(mockDaoJobListing);
        placeServ = new PlaceServiceImpl(mockDaoPlace);
        companyServ = new CompanyServiceImpl(mockDAOCompany, mockDaoPlace);
        listingStatusServ = new ListingStatusServiceImpl(mockDAOListingStatus);

        //make objects required for job listing 
        place1 = new Place(1L, "Phoenix", "AZ", "US");
        company1 = new Company(1L, "Amazon", "email@email.com", place1);
        listingStatus1 = new ListingStatus(1, "active");

        place2 = new Place(2L, "San Franciso", "CAL", "US");
        company2 = new Company(2L, "Apple", "email2@email.com", place1);
        listingStatus2 = new ListingStatus(2, "closed");


        //dummy DB setup
        jobListing1 = new JobListing(1L, "description", place1, company1, listingStatus1);
        jobListing2 = new JobListing(2L, "description", place2, company2, listingStatus1);
        
        
        dummyDB = new ArrayList<JobListing>();

        dummyDB.add(jobListing1);
        dummyDB.add(jobListing2);
        
    }

    @Test
    @Order(1)
    @DisplayName("1. Mock validation sanity test")
    void checkMockInjection() {
		assertThat(mockDaoJobListing).isNotNull();
		assertThat(jobListingServ).isNotNull();

        assertThat(mockDaoPlace).isNotNull();
		assertThat(placeServ).isNotNull();
        
        assertThat(mockDAOCompany).isNotNull();
		assertThat(companyServ).isNotNull();
        
        assertThat(mockDAOListingStatus).isNotNull();
		assertThat(listingStatusServ).isNotNull();
       
	}
    @Test
    @Order(2)
    @DisplayName("2. Create job listing Test")
    void testCreateJobListing(){
        //arrange step
        Place place3 = new Place(3L, "Dallas", "Texas", "US");
        Company company3 = new Company(3L, "Tesla", "email3@email.com", place3);
        ListingStatus listingStatus1 = new ListingStatus(1, "active");
        
        JobListing jobListing3 = new JobListing(3L, "desc", place3, company3,listingStatus1);
    

        jobListing3.setId(4L);

        //here we will tell mockito what type of behavior to expect from calling certain methods from our dao
        when(mockDaoJobListing.save(jobListing3)).thenReturn(jobListing3);
        when(mockDaoPlace.save(place3)).thenReturn(place3);
        //act + assert step        
        assertEquals(true,jobListingServ.createListing(jobListing3, place3, company3));




    }
    @Test
	@Order(3)
	@DisplayName("3. Get Job Lisitng by location")
	void testGetJobLisitngByLocation() {
		//arrange step already done in setup
		//here we will tell mockito what type of behavior to expect from calling certain methods from our dao
        when(jobListingServ.getAllJobListingByLocation(place1)).thenReturn(dummyDB);
        
		//act + assert step
		assertEquals(dummyDB, jobListingServ.getAllJobListingByLocation(place1));
	}

    @Test
	@Order(4)
	@DisplayName("4. Update Job Listing")
	void testUpdateJobListing() {
       
        listingStatus2 = new ListingStatus(2, "closed");
        
        jobListing2.setStatus(listingStatus2);
    
		
		when(jobListingServ.getListing(2L)).thenReturn(jobListing2);
		when(mockDaoJobListing.save(jobListing2)).thenReturn(jobListing2);
		
		assertEquals(true, jobListingServ.updateJobListing(jobListing2));
	}
    @Test
	@Order(5)
	@DisplayName("5. Delete Job listing")
	void testDeleteJobListing() {
        doNothing().when(mockDaoJobListing).delete(jobListing1);
		//act + assert step
		assertEquals(true, jobListingServ.deleteListing(jobListing1));
        
	}
    
}
