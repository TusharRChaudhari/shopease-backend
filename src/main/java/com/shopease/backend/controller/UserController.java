package com.shopease.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopease.backend.entity.User;
import com.shopease.backend.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController 
{
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public User registerUser(@RequestBody User user)
	{
		return userService.registerUser(user);
	}
	
	@GetMapping("/user/{email}")
	public User findUser(@PathVariable String email)
	{
		return userService.findByEmail(email);
	}
	
	
}


/*
 * What is a Controller? 
 * A controller: receives API requests calls the service
 * layer returns responses back to the client (Postman / frontend)
 * 
 * Example: POST /api/auth/register POST /api/auth/login
 */
