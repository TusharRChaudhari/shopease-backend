package com.shopease.backend.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.shopease.backend.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService 
{
	@Value("${jwt.secret}")
	private String secretKey;
	
	@Value("${jwt.expiration}")
	private long jwtExpirationMs;

	/* 	JWT needs a cryptographic key to sign the token.
		We convert your secret key text into a valid Key object. 	 */
	private Key getSignInKey()
	{
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}
	
	public String generateToken(User user)
	{
		Map<String,Object> claims = new HashMap<>();
		
		claims.put("role", user.getRole().name());
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(user.getEmail())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
				.signWith(getSignInKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	
	/*This verifies:
		>Signature is correct
		>Token is legitimate
		>Not forged*/
	public String extractUsername(String token)
	{
		return extractAllClaims(token).getSubject();
	}
	
	public Claims extractAllClaims(String token)
	{
		return Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	// Validates: >Is token not expired? 
	// >Does token belong to this user?
	public boolean isTokenValid(String token, UserDetails  userDetails )
	{
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) &&
				!isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) 
	{
		return extractAllClaims(token).getExpiration().before(new Date());
	}
}
/*
This class handles creating and validating JWT tokens.

public String generateToken(User user) {}
This creates a token like this:-

Header 
{
    "alg": "HS256"
}

Payload 
{
    "sub": "a@a.com",
    "role": "CUSTOMER",
    "iat": now,
    "exp": 24hrs later
}

Signature 
{
    HMACSHA256(secret)
}
*/