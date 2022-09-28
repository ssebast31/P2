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
@Table(name="users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "username"})})
public class User {
	public User(String username, String firstname, String lastname, String email, String password, UserRole role, Company company) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.role = role;
		this.company = company;
	}

	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="username" , nullable=false)
	private String username;
	
	@Column(name="firstname", nullable=true)
	private String firstname;
	
	@Column(name="lastname" , nullable=true)
	private String lastname;
	
	@Column(name="email"    , nullable = false)
	private String email;
	
	@Column(name="password" , nullable = false)
	private String password;
	
	@JoinColumn(name="user_role_id", referencedColumnName="user_role_id", nullable=false)
	@ManyToOne
	private UserRole role;
	
	@JoinColumn(name="company_id", referencedColumnName="company_id", nullable=true)
	@ManyToOne
	private Company company;
}
