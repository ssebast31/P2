package com.jobly.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="places")
public class Place {
	public Place(String city, String state, String country) {
		this.city = city;
		this.state = state;
		this.country = country;
	}

	@Column(name="place_id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="city", nullable = false)
	private String city;
	
	@Column(name="state", nullable = true)
	private String state;
	
	@Column(name="country", nullable = false)
	private String country;
}
