package com.jobly.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="application_status", uniqueConstraints = {@UniqueConstraint(columnNames = {"status"})})
public class ApplicationStatus {
    

	@Column(name="status_id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="status", nullable = false)
	private String status;

	public long getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}


}
