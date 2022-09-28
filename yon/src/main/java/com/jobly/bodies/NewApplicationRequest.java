package com.jobly.bodies;

import com.jobly.models.Application;
import com.jobly.models.User;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class NewApplicationRequest {
	private Application application;
	private User user;
	private long listingId;
	public Application getApplication() { return this.application; }
	public User getUser() { return this.user; }
	public long getListingId() { return this.listingId; }
}
