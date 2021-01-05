package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.entities.Category;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.repositories.CategoryRepository;
import com.blog.reviewwebsite.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Category getOneById(Long id) {
        return categoryRepository.getOne(id);
    }

    public void updateOrSaveCategory(Category category, User user) {
        User dbUser = userRepository.getOne(user.getId());
        dbUser.addCategory(category);
        dbUser.addFollowedCategory(category);
        categoryRepository.save(category);
    }

    public Set<Category> getCategoriesAndInitializeFollowers() {
        return categoryRepository.getCategoriesWithFollowersInitialized();
    }

    public Page<Category> getAllCategories(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return categoryRepository.findAll(pageable);
    }

    public Page<Category> getAllCategoriesUserFollows(User user, int pageNumber) {
        getCategoriesAndInitializeFollowers();
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
