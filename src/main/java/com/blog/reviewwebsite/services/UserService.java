package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.controller.Roles;
import com.blog.reviewwebsite.entities.Role;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.entities.UserDTO;
import com.blog.reviewwebsite.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.getOne(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void createUser(UserDTO user) {
        User dbUser = new User();
        dbUser.setPassword(passwordEncoder.encode(user.getPassword()));
        dbUser.setUsername(user.getUsername());
        Role role = roleService.getOneByName(Roles.USER);
        dbUser.addRole(role);
        saveUser(dbUser);
    }

    public void updateUserDescription(User user) {
        User dbUser = userRepository.getOne(user.getId());
        dbUser.setProfileDescription(user.getProfileDescription());
        saveUser(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public boolean userHasFile(User user) {
        return !user.getFiles().isEmpty();
    }

    public void toggleIncognito(Long userId) {
        User user = userRepository.getOne(userId);
        user.setIncognito(!user.isIncognito());
        saveUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }

}
