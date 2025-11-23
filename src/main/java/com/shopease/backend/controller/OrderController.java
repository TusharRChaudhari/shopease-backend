package com.shopease.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopease.backend.entity.Order;
import com.shopease.backend.security.AuthUtil;
import com.shopease.backend.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController 
{
	private final OrderService orderService;
	private final AuthUtil authUtil;
	
	public OrderController(OrderService orderService,AuthUtil authUtil)
	{
		this.orderService = orderService;
		this.authUtil = authUtil;
	}
	
	@PostMapping("/place")
	public ResponseEntity<Order> placeOrder()
	{
		String email = authUtil.getLoggedInEmail();
		Order order = orderService.placeOrder(email);
		
		if(order == null)
			return ResponseEntity.badRequest().build();
		
		return ResponseEntity.ok(order);
		
	}
	
	@GetMapping
	public ResponseEntity<List<Order>> getUserOrders()
	{
		String email = authUtil.getLoggedInEmail();
		List<Order> orders = orderService.getOrdersByUser(email);
		return ResponseEntity.ok(orders);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getOrderById(@PathVariable Long orderId)
	{
		Order order = orderService.getOrderById(orderId);
		
		if (order == null)
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(order);
	}
	
	@PutMapping("/cancel/{orderId}")
	public ResponseEntity<?> cancelOrder(@PathVariable Long orderId)
	{
		Order cancelledOrder = orderService.cancelOrder(orderId);
		
		if (cancelledOrder == null)
			return ResponseEntity.badRequest().body("Cannot cancel this order");
		
		return ResponseEntity.ok(cancelledOrder);
	}
}
