package com.shopease.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopease.backend.entity.Product;
import com.shopease.backend.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController 
{
	@Autowired
	ProductService productService;
	
	// Add Product
	@PostMapping
	public ResponseEntity<Product> addProduct(@RequestBody Product product)
	{
		Product saved = productService.addProduct(product);
		return ResponseEntity.ok(saved);
	}
	
	// Get All Products
	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts()
	{
		return ResponseEntity.ok(productService.getAllProducts());
	}
	
	// Get Product By ID
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable Long id)
	{
		Product product = productService.getProductById(id);
		if(product == null)
		{
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(product);
	}
	
	// Update Product By ID
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,@RequestBody Product updatedProduct) 
    {
        Product product = productService.updateProduct(id, updatedProduct);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }
    
	// Delete Product
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id)
	{
		productService.deleteProduct(id);
		return ResponseEntity.ok("Product deleted Successfully");
	}
}
