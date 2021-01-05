package com.blog.reviewwebsite.repositories;

import com.blog.reviewwebsite.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query(value = "SELECT chat_id AS id FROM users_chat WHERE user_id= :userId", nativeQuery = true)
    Set<Chat> getAllThisUserChats(Long userId);

}
