package com.jobly.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.JoinColumn;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="job_listing")
public class JobListing {
	public JobListing(String description, Place place, Company company, ListingStatus status) {
		this.description = description;
		this.place = place;
		this.company = company;
		this.status = status;
	}
	
	@Column(name="listing_id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="job_description", nullable = false)
	private String description;
	
	@JoinColumn(name="place_id", referencedColumnName="place_id", nullable = false)
	@ManyToOne
	private Place place;
	
	@JoinColumn(name="company_id", referencedColumnName="company_id", nullable = false)
	@ManyToOne
	private Company company;
	
	@JoinColumn(name="status_id", referencedColumnName="status_id", nullable = false)
	@ManyToOne
	private ListingStatus status;

}
