package com.shopease.backend.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil 
{
	public String getLoggedInEmail() 
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth == null) 
			return null;
		
		return auth.getName(); // our username/email in UserDetails
	}
}
/*
This allows us to get the email of the logged-in user anywhere. Helper method to get logged in user

*/