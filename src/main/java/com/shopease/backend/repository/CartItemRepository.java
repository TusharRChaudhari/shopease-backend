package com.shopease.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shopease.backend.entity.CartItem;

public interface CartItemRepository  extends JpaRepository<CartItem, Long>
{
	
}
