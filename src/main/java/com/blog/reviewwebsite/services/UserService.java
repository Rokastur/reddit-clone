package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.controller.Roles;
import com.blog.reviewwebsite.entities.Category;
import com.blog.reviewwebsite.entities.Review;
import com.blog.reviewwebsite.entities.Role;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.repositories.ReviewRepository;
import com.blog.reviewwebsite.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private ReviewRepository reviewRepository;
    private CategoryService categoryService;
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ReviewRepository reviewRepository, CategoryService categoryService, RoleService roleService) {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.categoryService = categoryService;
        this.roleService = roleService;
    }

    public User getUser(Long id) {
        return userRepository.getOne(id);
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleService.getOneByName(Roles.USER);
        user.getRoles().add(role);
        return userRepository.save(user);
    }

    public User updateUserDescription(User user) {
        User dbUser = userRepository.getOne(user.getId());
        dbUser.setProfileDescription(user.getProfileDescription());
        return userRepository.save(dbUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Page<Review> getUserReviews(int pageNumber, Long id) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        User user = userRepository.getOne(id);
        return reviewRepository.findAllByUser(user, pageable);
    }

    @Transactional
    public boolean userHasFile(User user) {
        return !user.getFiles().isEmpty();
    }

    public User toggleIncognito(Long userId) {
        User user = userRepository.getOne(userId);
        user.setIncognito(!user.isIncognito());
        return userRepository.save(user);
    }

    public User followCategory(Long userId, Long id) {
        Category category = categoryService.getOneById(id);
        User user = userRepository.getOne(userId);
        if (user.getFollowedCategories().contains(category)) {
            user.removeCategory(category);
        } else {
            user.addCategory(category);
        }
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }

    public boolean validNewUser(User user) {
        return !userRepository.findUserByUsername(user.getUsername()).isPresent();
    }

    public boolean passwordsMatch(User user) {
        return user.getPassword().equals(user.getRetypePassword());
    }
}
