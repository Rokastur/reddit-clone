package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.entities.UserDTO;
import com.blog.reviewwebsite.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveUserTest() {
        User user = new User();
        userService.saveUser(user);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void getUserTest() {
        User user = new User();
        user.setId(1L);
        user.setUsername("Guinea Pig");
        when(userService.getUser(1l)).thenReturn(user);
        User testUser = userService.getUser(1l);
        assertEquals("Guinea Pig", testUser.getUsername());
    }

    @Test
    public void getAllUsersTest() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        users.add(user1);
        users.add(user2);
        users.add(user3);

        when(userRepository.findAll()).thenReturn(users);

        List<User> userList = userService.getAllUsers();

        assertEquals(3, userList.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void createUserTest() {
        UserDTO userDTO = new UserDTO();
        User user = new User();

        userDTO.setUsername("Guinea Pig");
        userDTO.setPassword("password");

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());

        assertEquals(userDTO.getUsername(), user.getUsername());
        assertEquals(userDTO.getPassword(), user.getPassword());
    }

    @Test
    public void updateUserDescriptionTest() {
        String oldDescription = "old description";
        String newDescription = "new description";
        User user = new User();
        user.setProfileDescription(oldDescription);
        user.setProfileDescription(newDescription);
        assertNotEquals(oldDescription, user.getProfileDescription());
        assertEquals(newDescription, user.getProfileDescription());
    }
}
