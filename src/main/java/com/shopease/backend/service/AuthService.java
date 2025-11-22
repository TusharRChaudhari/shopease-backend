package com.shopease.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopease.backend.dto.AuthenticationRequest;
import com.shopease.backend.dto.AuthenticationResponse;
import com.shopease.backend.dto.RegisterRequest;
import com.shopease.backend.entity.User;
import com.shopease.backend.repository.UserRepository;
import com.shopease.backend.security.JwtService;

@Service
public class AuthService 
{
	private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) 
	{
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}
    
    // Registration
	public AuthenticationResponse register(RegisterRequest request)
	{
		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));  // hash password
		user.setRole(request.getRole());
		
		userRepository.save(user);
		
		String token = jwtService.generateToken(user);
		
		return new AuthenticationResponse(token, "User registered Successfully");
	}
	
	// Login
	public AuthenticationResponse login(AuthenticationRequest request)
	{
		User user = userRepository.findByEmail(request.getEmail());
		
		if (user == null)
			return new AuthenticationResponse(null, "Invalid email");
		
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
			return new AuthenticationResponse(null, "Invalid password");
		
		String token = jwtService.generateToken(user);
		
		return new AuthenticationResponse(token, "Login successfull");
			
	}
}
