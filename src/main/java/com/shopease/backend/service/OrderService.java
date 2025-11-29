package com.shopease.backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shopease.backend.entity.Address;
import com.shopease.backend.entity.Cart;
import com.shopease.backend.entity.CartItem;
import com.shopease.backend.entity.Order;
import com.shopease.backend.entity.OrderItem;
import com.shopease.backend.entity.OrderStatus;
import com.shopease.backend.entity.User;
import com.shopease.backend.repository.AddressRepository;
import com.shopease.backend.repository.CartRepository;
import com.shopease.backend.repository.OrderRepository;
import com.shopease.backend.repository.UserRepository;

@Service
public class OrderService 
{
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
   
    
	public OrderService(OrderRepository orderRepository,
			CartRepository cartRepository, 
			UserRepository userRepository,
			AddressRepository addressRepository) 
	{
		this.orderRepository = orderRepository;
		this.cartRepository = cartRepository;
		this.userRepository = userRepository;
		this.addressRepository = addressRepository;
	}
    
	public Order placeOrder(String email, Long addressId) 
	{
		// Get user
		User user = userRepository.findByEmail(email);
		
		if(user==null)
			return null;
		
		Address address = addressRepository.findById(addressId).orElse(null);
		if (address == null) return null;
		
		Cart cart = cartRepository.findByUser(user);
		
		if(cart.getCartItems().isEmpty())
		{
			return null;  // empty cart
		}
		
		 // Create order
		Order order = new Order();
		order.setUser(user);
		order.setCreatedAt(LocalDateTime.now());
		order.setStatus(OrderStatus.PENDING);
		
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
		order.setShippingAddress(address);
		
		// Save order and items
		Order saveOrder = orderRepository.save(order);
		
		// clear cart
		cart.getCartItems().clear();
		cart.setTotalPrice(0);
		cartRepository.save(cart);
		
		return saveOrder;
	}
	
	public List<Order> getOrdersByUser(String email) 
	{
	    User user = userRepository.findByEmail(email);
	    if (user == null)
	        return null;

	    return orderRepository.findByUserId(user.getId());
	}

	
	public Order getOrderById(Long orderId)
	{
		return orderRepository.findById(orderId).orElse(null);
	}
	
	public Order cancelOrder(Long orderId)
	{
		Order order = orderRepository.findById(orderId).orElse(null);
		
		if(order == null)
			return null;
		
		if(order.getStatus() != OrderStatus.PENDING)
			return null;
		
		order.setStatus(OrderStatus.CANCELLED);
		
		return orderRepository.save(order);
	}
	
}



