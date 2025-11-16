package com.shopease.backend.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.shopease.backend.entity.Category;
import com.shopease.backend.repository.CategoryRepository;


@Service
public class CategoryService 
{
	private final CategoryRepository categoryRepository;
	
	public CategoryService(CategoryRepository categoryRepository)
	{
		this.categoryRepository = categoryRepository;
	}
	
	public Category createCategory(Category category)
	{
		return categoryRepository.save(category);
	}
	
	public List<Category> getAllCategories()
	{
		return categoryRepository.findAll();
	}
}
