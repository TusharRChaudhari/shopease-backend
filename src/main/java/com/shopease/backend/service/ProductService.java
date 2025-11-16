package com.shopease.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopease.backend.entity.Category;
import com.shopease.backend.entity.Product;
import com.shopease.backend.repository.CategoryRepository;
import com.shopease.backend.repository.ProductRepository;

@Service
public class ProductService 
{
	@Autowired
	ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	
	//addProduct
	public Product addProduct(Product product)
	{
		Long categoryId = product.getCategory().getId();
		
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(()->new RuntimeException("Category not found with ID: "+ categoryId));
		
		product.setCategory(category);
		
		return productRepository.save(product);
	}
	
	//getAllProducts
	public List<Product> getAllProducts()
	{
		return productRepository.findAll();
	}
	
	//getProductById
	public Product getProductById(Long id)
	{
		return productRepository.findById(id).orElse(null);
	}
	
	// Update product (including category)
    public Product updateProduct(Long id, Product updatedProduct) 
    {
        Product existing = productRepository.findById(id).orElse(null);

        if (existing == null)
        {
            return null;
        }

        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());

        // Update category
        if (updatedProduct.getCategory() != null)
        {
            Long categoryId = updatedProduct.getCategory().getId();
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
            existing.setCategory(category);
        }

        return productRepository.save(existing);
    }

	
	//deleteProduct
	public void deleteProduct(Long id)
	{
		productRepository.deleteById(id);
	}
}
