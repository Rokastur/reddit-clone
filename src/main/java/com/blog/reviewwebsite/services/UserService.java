package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long id) {
        User user = userRepository.getOne(id);
        return user;
    }

    public User updateOrSaveUser(User user) {
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
}
