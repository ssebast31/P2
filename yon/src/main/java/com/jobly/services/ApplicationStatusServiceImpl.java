package com.jobly.services;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobly.models.ApplicationStatus;
import com.jobly.repositories.ApplicationStatusRepository;

@Service
@Transactional
public class ApplicationStatusServiceImpl implements ApplicationStatusService {

	@Autowired
	private ApplicationStatusRepository appstatusrepo;

	public ApplicationStatusServiceImpl(ApplicationStatusRepository dao) {
		this.appstatusrepo = dao;
	}

	@Override
	public int createStatus(ApplicationStatus status) {
		if (appstatusrepo.existsByStatus(status.getStatus())) {
			return -1;
		}

		// If creation is successful return 1 else 0
		long statusKey = appstatusrepo.save(status).getId();
		return (statusKey > 0) ? 1 : 0;
	}

	@Override
	public List<ApplicationStatus> getAllApplicationStatus() {
		return appstatusrepo.findAll();
	}

	@Override
	public boolean updateListingStatus(ApplicationStatus status) {
		//return appstatusrepo.update(status.getStatus(), status.getId());
		ApplicationStatus target = appstatusrepo.getReferenceById(status.getId());
		target.setId(status.getId());
		target.setStatus(status.getStatus());
		return (appstatusrepo.save(target) != null) ? true : false;

	}

	@Override
	public boolean deleteStatus(ApplicationStatus status) {
		appstatusrepo.delete(status);
		return true;
	}

	@Override
	public ApplicationStatus getStatus(long statusId) {
		return appstatusrepo.getReferenceById(statusId);

	}

}
