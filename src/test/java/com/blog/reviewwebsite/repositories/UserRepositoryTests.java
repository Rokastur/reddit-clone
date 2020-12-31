package com.blog.reviewwebsite.repositories;

import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void findUserByUsernameTest() {
        User user = new User();
        user.setUsername("Guinea Pig");
        user.setPassword("test");
        entityManager.persist(user);
        entityManager.flush();

        Optional<User> dbUser = userRepository.findUserByUsername(user.getUsername());

        assertEquals(user.getUsername(), dbUser.get().getUsername());
    }


}
