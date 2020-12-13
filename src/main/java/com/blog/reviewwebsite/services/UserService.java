package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.controller.Roles;
import com.blog.reviewwebsite.entities.Category;
import com.blog.reviewwebsite.entities.Role;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private CategoryService categoryService;
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, CategoryService categoryService, RoleService roleService) {
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.roleService = roleService;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.getOne(id);
    }

    public boolean createUser(User user) {
        if (validNewUser(user) && passwordsMatch(user)) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Role role = roleService.getOneByName(Roles.USER);
            user.getRoles().add(role);
            userRepository.save(user);
            return true;
        } else return false;
    }

    public User updateUserDescription(User user) {
        User dbUser = userRepository.getOne(user.getId());
        dbUser.setProfileDescription(user.getProfileDescription());
        return userRepository.save(dbUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
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
            user.removeFollowedCategory(category);
        } else {
            user.addFollowedCategory(category);
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
