package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long id) {
        User user = userRepository.getOne(id);
        return user;
    }

    public User updateOrSaveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
