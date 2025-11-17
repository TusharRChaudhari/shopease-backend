package com.shopease.backend.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.shopease.backend.entity.Cart;
import com.shopease.backend.entity.CartItem;
import com.shopease.backend.entity.Product;
import com.shopease.backend.entity.User;
import com.shopease.backend.repository.CartItemRepository;
import com.shopease.backend.repository.CartRepository;
import com.shopease.backend.repository.ProductRepository;
import com.shopease.backend.repository.UserRepository;

@Service
public class CartService 
{
	private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    
	public CartService(
			CartRepository cartRepository,
			CartItemRepository cartItemRepository,
			ProductRepository productRepository,
			UserRepository userRepository
			)
	{
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
	}
	
    // Utility method → calc total
	private double calculateTotal(Cart cart)
	{
		return cart.getCartItems()
				.stream()
				.mapToDouble(CartItem::getPrice)
				.sum();
	}
	
	// 1 → Get cart of a user
	public Cart getCartByUser(Long userId)
	{
		User user = userRepository.findById(userId).orElse(null);
		
		if(user == null)
		{	return null;}
		
		Cart cart = cartRepository.findByUser(user);
		// If cart does not exist → create empty cart
		if (cart == null)
		{
			cart = new Cart();
			cart.setUser(user);
			cart.setCartItems(new ArrayList<>());
			cart.setTotalPrice(0);
			cart = cartRepository.save(cart);
		}
		return cart;		
	}
	
	// 2 → Add product to cart
	public Cart addProductToCart(Long userId,Long productId,int quantity)
	{
		Cart cart = getCartByUser(userId); //ensures cart exists
		Product product = productRepository.findById(productId).orElse(null);
		
		if(product==null)
			return null;
		
        // Create CartItem
		CartItem item = new CartItem();
		item.setCart(cart);
		item.setProduct(product);
		item.setQuantity(quantity);
		item.setPrice(product.getPrice()*quantity);
		
		cart.getCartItems().add(item);
		cartItemRepository.save(item);
		
		// Update total price
        cart.setTotalPrice(calculateTotal(cart));
        
        return cartRepository.save(cart);		
	}
	
    // 3 → Remove item from cart
	public Cart removeItemFromCart(Long userId, Long cartItemId)
	{
		Cart cart = getCartByUser(userId);
		
		CartItem item = cartItemRepository.findById(cartItemId).orElse(null);
		
		if(item==null)
			return cart;
		
		cart.getCartItems().remove(item);
		cartItemRepository.delete(item);
		
		cart.setTotalPrice(calculateTotal(cart));
		return cartRepository.save(cart);
	}
	
	
}






















