package com.shopease.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shopease.backend.entity.User;
import com.shopease.backend.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserRepository userRepository;

	@Override
	public User registerUser(User user) 
	{ 
		// simple registration (we will add password encryption later)
		return userRepository.save(user);
	}

	@Override
	public User findByEmail(String email) 
	{
		return userRepository.findByEmail(email);
	}

}

/*
 * What is a Service?
 * A Service is where your business logic lives.
 * 
 * Examples of business logic:
 * when a user registers → encrypt the password
 * check if email already exists
 * login → verify password
 * fetch user details
 * update profile
 * Service = logic layer.
 */