package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.entities.Category;
import com.blog.reviewwebsite.entities.Review;
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
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private ReviewRepository reviewRepository;
    private CategoryService categoryService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ReviewRepository reviewRepository, CategoryService categoryService) {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.categoryService = categoryService;
    }

    public Page<Review> getUserReviews(int pageNumber, Long id) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        User user = userRepository.getOne(id);
        return reviewRepository.findAllByUser(user, pageable);
    }

    public User getUser(Long id) {
        User user = userRepository.getOne(id);
        return user;
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).get();
    }

    @Transactional
    public boolean userHasFile(User user) {
        return !user.getFiles().isEmpty();
    }


    public User toggleIncognito(Long userId) {
        User user = userRepository.getOne(userId);
        user.setIncognito(!user.isIncognito());
        userRepository.save(user);
        return user;
    }

    public User updateUser(User user) {
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
        userRepository.save(user);
        return user;
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }

    public boolean validNewUser(User user) {
        if (userRepository.findUserByUsername(user.getUsername()).isPresent()) {
            return false;
        } else return true;
    }

    public boolean passwordsMatch(User user) {
        if (user.getPassword().equals(user.getRetypePassword())) {
            return true;
        }
        return false;
    }
}
