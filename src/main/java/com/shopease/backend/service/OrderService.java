package com.shopease.backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shopease.backend.entity.Cart;
import com.shopease.backend.entity.CartItem;
import com.shopease.backend.entity.Order;
import com.shopease.backend.entity.OrderItem;
import com.shopease.backend.entity.User;
import com.shopease.backend.repository.OrderItemRepository;
import com.shopease.backend.repository.OrderRepository;
import com.shopease.backend.repository.UserRepository;

@Service
public class OrderService 
{
    private OrderRepository orderRepository;
    private CartService cartService;
    private UserRepository userRepository;
    
	public OrderService(OrderRepository orderRepository,
			OrderItemRepository orderItemRepository,
			CartService cartService, 
			UserRepository userRepository) 
	{
		this.orderRepository = orderRepository;
		this.cartService = cartService;
		this.userRepository = userRepository;
	}
    
	public Order placeOrder(Long userId) 
	{
		// Get user
		User user = userRepository.findById(userId).orElse(null);
		
		if(user==null)
			return null;
		
		Cart cart = cartService.getCartByUser(userId);
		
		if(cart.getCartItems().isEmpty())
		{
			return null;  // empty cart
		}
		
		 // Create order
		Order order = new Order();
		order.setUser(user);
		order.setCreatedAt(LocalDateTime.now());
		order.setStatus("PENDING");
		
		List<OrderItem> orderItems = new ArrayList<>();
		double total = 0;
		
		for (CartItem cartItem: cart.getCartItems())
		{
			OrderItem oi = new OrderItem();
			oi.setOrder(order);
			oi.setProduct(cartItem.getProduct());
			oi.setQuantity(cartItem.getQuantity());
			oi.setPrice(cartItem.getPrice());
			
			total += cartItem.getPrice();
            orderItems.add(oi);
		}
		
		order.setItems(orderItems);
		order.setTotalAmount(total);
		
		// Save order and items
		Order saveOrder = orderRepository.save(order);
		
		// clear cart
		cart.getCartItems().clear();
		cart.setTotalPrice(0);
		 
		return saveOrder;
	}
	
	public List<Order> getOrdersByUser(Long userId) 
	{
	    return orderRepository.findByUserId(userId);
	}

}



