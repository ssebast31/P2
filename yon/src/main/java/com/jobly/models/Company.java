package com.jobly.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.JoinColumn;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="companies", uniqueConstraints = {@UniqueConstraint(columnNames = {"company_name"})})
public class Company {
	
	public Company(String name, String email, Place place) {
		this.name = name;
		this.email = email;
		this.place = place;
	}
	
	@Column(name="company_id")
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="company_name", nullable=false)
	private String name;
	
	@Column(name="email"       , nullable = false)
	private String email;
	
	@JoinColumn(name="place_id", nullable = true)
	@ManyToOne
	private Place place;
}
