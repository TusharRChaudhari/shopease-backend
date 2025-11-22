package com.shopease.backend.dto;

public class AuthenticationRequest 
{
	private String email;
	private String password;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}

/*
>DTOs (Data Transfer Objects) are simple classes used ONLY to exchange data in APIs.
>A Data Transfer Object is a design pattern that allows for the exchange of data 
between software application subsystems or layers.
*/