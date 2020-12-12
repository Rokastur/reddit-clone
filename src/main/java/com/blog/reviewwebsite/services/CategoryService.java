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

    public Category getOneById(Long id) {
        return categoryRepository.getOne(id);
    }

    public Category updateOrSaveCategory(Category category, User user) {
        category.setUser(user);
        return categoryRepository.save(category);
    }

    public Page<Category> getAllCategories(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return categoryRepository.findAll(pageable);
    }

    public Page<Category> getAllCategoriesUserFollows(User user, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return categoryRepository.findAllFollowedByUser(user.getId(), pageable);
    }

    public Page<Category> getAllCategoriesByPostCountDesc(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return categoryRepository.findAllOrderByPostCountDesc(pageable);
    }

    public Page<Category> getAllCategoriesByPostCountAsc(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return categoryRepository.findAllOrderByPostCountAsc(pageable);
    }

    public Page<Category> getAllCategoriesByFollowersCountDesc(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return categoryRepository.findAllOrderByFollowersCountDesc(pageable);
    }

    public Page<Category> getAllCategoriesByFollowersCountAsc(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return categoryRepository.findAllOrderByFollowersCountAsc(pageable);
    }

    public Page<Category> getAllCategoriesByNewestPost(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return categoryRepository.findAllOrderByNewestPost(pageable);
    }

    public Page<Category> getAllCategoriesByOldestPost(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return categoryRepository.findAllOrderByOldestPost(pageable);
    }
}
