package com.jobly.bodies;

import com.jobly.models.Company;
import com.jobly.models.Place;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class NewCompanyRequest {
	private Company company;
	private Place place;
	public Company getCompany() { return this.company; }
	public Place getPlace() { return this.place; }
}
