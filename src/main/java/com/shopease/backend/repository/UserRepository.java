package com.shopease.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shopease.backend.entity.User;

public interface UserRepository extends JpaRepository<User,Long>
{
	// Custom finder method to find user by email
	User findByEmail(String email);
}


/*
 * What is a Repository? (Simple Explanation)
 * 
 * A repository is a class that talks to the database. Instead of writing SQL
 * manually, Spring JPA lets you access data using methods.
 */