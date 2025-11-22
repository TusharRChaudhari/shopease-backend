package com.shopease.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shopease.backend.entity.User;
import com.shopease.backend.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
	@Autowired
	private UserRepository userRepository;
	
	/*This method is called automatically during login process.
	This converts your own User entity into a Spring Security UserDetails object.
	It maps your custom user → spring security user.
	& return a Spring Security UserDetails object. */
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException 
	{
		User user = userRepository.findByEmail(email);
		
		if (user==null)
			throw new UsernameNotFoundException("User not found: " + email);
		
		return org.springframework.security.core.userdetails.User
				.withUsername(user.getEmail())
				.password(user.getPassword())
				.roles(user.getRole().name())
				.build();
	}
	
}
