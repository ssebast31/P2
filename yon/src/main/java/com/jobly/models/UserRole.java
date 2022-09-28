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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="user_roles", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_role"})})
public class UserRole {
	public UserRole(String role) {
		this.role = role;
	}

	@Id
	@Column(name="user_role_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="user_role", nullable = false)
	private String role;
}
