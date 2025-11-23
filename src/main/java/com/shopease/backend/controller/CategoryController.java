package com.shopease.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopease.backend.entity.Category;
import com.shopease.backend.service.CategoryService;

@RestController
@RequestMapping("api/categories")
public class CategoryController 
{
	@Autowired
	private CategoryService categoryService;
	
	// Create Category
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Category createCategory(@RequestBody Category category)
	{
		return categoryService.createCategory(category);
	}
	
	// Get All Categories
    @GetMapping
    public List<Category> getAllCategories() 
    {
        return categoryService.getAllCategories();
    }
}
