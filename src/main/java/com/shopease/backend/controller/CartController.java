package com.shopease.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopease.backend.entity.Cart;
import com.shopease.backend.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController
{
	@Autowired
	private CartService cartService;
	
	// Get cart for user
	@GetMapping("/{userId}")
	public ResponseEntity<Cart> getCart(@PathVariable Long userId)
	{
		Cart cart = cartService.getCartByUser(userId);
		
		if(cart == null)
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(cart);
	}
	
	// Add product to cart
    @PostMapping("/{userId}/add/{productId}/{quantity}")
    public ResponseEntity<Cart> addProductToCart(
    		@PathVariable Long userId,
    		@PathVariable Long productId,
    		@PathVariable int quantity
    		)
    {
    	Cart cart = cartService.addProductToCart(userId, productId, quantity);
    	
    	if(cart==null)
    		return ResponseEntity.notFound().build();
    	
		return ResponseEntity.ok(cart);
    }
    
    // Remove cart item
    @DeleteMapping("/{userId}/remove/{cartItemId}")
    public ResponseEntity<Cart> removeItem(
    		@PathVariable Long userId,
    		@PathVariable Long cartItemId)
    {
    	Cart cart = cartService.removeItemFromCart(userId, cartItemId);
    	return ResponseEntity.ok(cart);
    }
}










