package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.entities.Category;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.repositories.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private UserService userService;

    public CategoryService(CategoryRepository categoryRepository, UserService userService) {
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    public Category getOneById(Long id) {
        return categoryRepository.getOne(id);
    }

    public void updateOrSaveCategory(Category category, User user) {
        User dbUser = userService.getUser(user.getId());
        dbUser.addCategory(category);
        dbUser.addFollowedCategory(category);
        categoryRepository.save(category);
    }

    public void followCategory(User user, Long id) {
        if (user == null) {
            return;
        }
        Category category = categoryRepository.findById(id).get();
        User dbUser = userService.getUser(user.getId());
        if (dbUser.getFollowedCategories().contains(category)) {
            dbUser.removeFollowedCategory(category);
        } else {
            dbUser.addFollowedCategory(category);
        }
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
