package com.jobly.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Lob;
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
@Table(name="applications")
public class Application {
	
	public Application(User applicant, byte[] resume, JobListing listing, ApplicationStatus status) {
		this.applicant = applicant;
		this.resume = resume;
		this.listing = listing;
		this.status = status;
	}
	
	@Column(name="application_id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@JoinColumn(name="applicant_id", referencedColumnName="user_id", nullable = false)
	@ManyToOne
	private User applicant;
	
	@Column(name="resume", nullable = true)
	@Lob
	private byte[] resume;
	
	@JoinColumn(name="listing_id", referencedColumnName="listing_id", nullable = false)
	@ManyToOne
	private JobListing listing;

	@JoinColumn(name="status_id", referencedColumnName="status_id", nullable = true)
	@ManyToOne
	private ApplicationStatus status;
}
