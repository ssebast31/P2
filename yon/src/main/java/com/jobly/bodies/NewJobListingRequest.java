package com.jobly.bodies;

import com.jobly.models.Company;
import com.jobly.models.JobListing;
import com.jobly.models.Place;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class NewJobListingRequest {
	private JobListing listing;
	private Place place;
	private Company company;
	public JobListing getListing() { return listing; }
	public Place getPlace() { return place; }
	public Company getCompany() { return company; }
}