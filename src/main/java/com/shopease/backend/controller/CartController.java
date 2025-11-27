package com.shopease.backend.controller;

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
    private final CartService cartService;

    public CartController(CartService cartService)
    {
        this.cartService = cartService;
    }
	
	// Get cart for user
	@GetMapping
	public ResponseEntity<Cart> getCart()
	{
		return ResponseEntity.ok(cartService.getCart());
	}
	
	// Add product to cart
    @PostMapping("/add/{productId}/{quantity}")
    public ResponseEntity<Cart> addProductToCart(
    		@PathVariable Long productId,
    		@PathVariable int quantity)
    {
    	Cart cart = cartService.addProductToCart(productId, quantity);
    	
    	if(cart==null)
    		return ResponseEntity.notFound().build();
    	
		return ResponseEntity.ok(cart);
    }
    
    // Remove cart item
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Cart> removeItem(
    		@PathVariable Long cartItemId)
    {
    	Cart cart = cartService.removeItemFromCart(cartItemId);
    	return ResponseEntity.ok(cart);
    }
}










