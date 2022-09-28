package com.jobly.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobly.models.ApplicationStatus;
import com.jobly.models.ListingStatus;
import com.jobly.models.UserRole;
import com.jobly.repositories.ApplicationStatusRepository;
import com.jobly.repositories.ListingStatusRepository;
import com.jobly.repositories.UserRoleRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Transactional
@Service("DatabaseInit")
public class DatabaseInitializerUtil {

	@Autowired
	private UserRoleRepository urrepo;
	
	@Autowired
	private ListingStatusRepository lsrepo;
	
	@Autowired
	private ApplicationStatusRepository asrepo;
	
	public void init() {
		urrepo.save(new UserRole(1L,"Employee"));
		urrepo.save(new UserRole(2L,"Employer"));
		urrepo.save(new UserRole(3L,"Admin"));
		
		lsrepo.save(new ListingStatus(1L,"Open"));
		lsrepo.save(new ListingStatus(2L,"Closed"));
		lsrepo.save(new ListingStatus(3L,"Expired"));
		
		asrepo.save(new ApplicationStatus(1L,"Pending"));
		asrepo.save(new ApplicationStatus(2L,"Viewed"));
		asrepo.save(new ApplicationStatus(3L,"Rejected"));
		asrepo.save(new ApplicationStatus(4L,"Accepted"));
	}
}