package com.shopease.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shopease.backend.entity.Cart;
import com.shopease.backend.entity.User;

public interface CartRepository extends JpaRepository<Cart, Long> 
{
	Cart findByUser(User user);
}
