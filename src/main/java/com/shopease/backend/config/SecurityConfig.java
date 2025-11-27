package com.shopease.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shopease.backend.security.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig 
{
	private final JwtAuthenticationFilter jwtAuthFilter;
	
	public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) 
	{
        this.jwtAuthFilter = jwtAuthFilter;
    }
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		http.csrf(csrf -> csrf.disable());
		
		http.sessionManagement(session ->
								session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.authorizeHttpRequests(auth -> auth
				// Public endpoints (login, register)
		        .requestMatchers("/auth/**").permitAll()

		        .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
		        .requestMatchers("/products/**").hasRole("ADMIN")
		        
		        // ADMIN ONLY
		        //.requestMatchers("/products/**").hasRole("ADMIN")
		        .requestMatchers("/api/categories/**").hasRole("ADMIN")

		        // Customer OR Admin
		        .requestMatchers("/cart/**", "/orders/**").hasAnyRole("CUSTOMER", "ADMIN")

		        // Everything else must be authenticated
		        .anyRequest().authenticated());
		
		http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationmanager(AuthenticationConfiguration config) throws Exception
	{
		return config.getAuthenticationManager();
	}
}



















