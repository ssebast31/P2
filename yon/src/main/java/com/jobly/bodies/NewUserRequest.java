package com.jobly.bodies;

import com.jobly.models.User;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequest {
	private User user;
	private String role;
	private String companyName;
	private long companyId;
	public User getUser() { return this.user; }
	public String getRole() { return this.role; }
	public String getCompanyName() { return companyName; }
	public long getCompanyId() { return companyId; }
	
}
