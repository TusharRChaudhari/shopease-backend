package com.shopease.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig 
{
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
}

/*
PasswordEncoder (BCrypt)
What is it?

It is a component that encrypts (hashes) passwords before storing them in the database.
We never store plain passwords.
*/

/*
Why BCrypt?
BCrypt:

Adds salt automatically 
Slow hashing → prevents brute force attacks 
Industry standard for authentication
 */

