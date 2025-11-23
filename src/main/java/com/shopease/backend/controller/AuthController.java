package com.shopease.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopease.backend.dto.AuthenticationRequest;
import com.shopease.backend.dto.AuthenticationResponse;
import com.shopease.backend.dto.RegisterRequest;
import com.shopease.backend.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController 
{
	private final AuthService authService;

	public AuthController(AuthService authService) 
	{
		this.authService = authService;
	}
	
	// REGISTER
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody  RegisterRequest request)
	{
		return ResponseEntity.ok(authService.register(request));
	}
	
	 // LOGIN
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request)
	{
		return ResponseEntity.ok(authService.login(request));
	}
}
