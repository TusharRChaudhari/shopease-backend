package com.shopease.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopease.backend.entity.Order;
import com.shopease.backend.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController 
{
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/place/{userId}")
	public ResponseEntity<Order> placeOrder(@PathVariable Long userId)
	{
		Order order = orderService.placeOrder(userId);
		
		if(order == null)
			return ResponseEntity.badRequest().build();
		
		return ResponseEntity.ok(order);
		
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long userId)
	{
		List<Order> orders = orderService.getOrdersByUser(userId);
		return ResponseEntity.ok(orders);
	}
}
