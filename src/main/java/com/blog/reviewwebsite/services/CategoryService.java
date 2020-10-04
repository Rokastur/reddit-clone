package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.entities.Category;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.repositories.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category updateOrSaveCategory(Category category, User user) {
        category.setUser(user);
        return categoryRepository.save(category);
    }

    public Page<Category> getAllCategories(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return categoryRepository.findAll(pageable);
    }
}
