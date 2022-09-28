package com.jobly.services;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobly.models.ListingStatus;
import com.jobly.repositories.ListingStatusRepository;

@Service
@Transactional
public class ListingStatusServiceImpl implements ListingStatusService {
	
	@Autowired
	private ListingStatusRepository lsrepo;
	
	public ListingStatusServiceImpl(ListingStatusRepository dao) {
		this.lsrepo = dao;
	}

	@Override
	public int createStatus(ListingStatus status){
		if (lsrepo.existsByStatus(status.getStatus())){
			return -1;
		}
		// If creation is successful return 1 else 0
		long statusKey = lsrepo.save(status).getId();
		
		return (statusKey > 0) ? 1 : 0;
		
	}

	@Override
	public boolean deleteStatus(ListingStatus status) {
		lsrepo.delete(status);
		return true;
	}

	@Override
	public List<ListingStatus> getAllListingStatus() {
		return lsrepo.findAll();
	}

	@Override
	public boolean updateListingStatus(ListingStatus listing) {
		ListingStatus target = lsrepo.getReferenceById(listing.getId());
		target.setStatus(listing.getStatus());
		return (lsrepo.save(target) != null) ? true : false;
	}

	@Override
	public ListingStatus getStatusById(Long id) {

		return lsrepo.getReferenceById(id);
	}

	
}
