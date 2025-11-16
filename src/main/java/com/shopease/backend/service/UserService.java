package com.shopease.backend.service;

import com.shopease.backend.entity.User;

public interface UserService 
{
	User registerUser(User user);
	
	User findByEmail(String email);
}
