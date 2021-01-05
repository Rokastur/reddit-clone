package com.blog.reviewwebsite.repositories;

import com.blog.reviewwebsite.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    @Query(value = "SELECT u from User u JOIN FETCH u.comments WHERE u.id =:id")
    User getOneWithCommentsInitialized(Long id);
}
