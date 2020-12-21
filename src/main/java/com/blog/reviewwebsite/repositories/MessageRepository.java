package com.blog.reviewwebsite.repositories;

import com.blog.reviewwebsite.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT * FROM message WHERE chat_id= :chatId", nativeQuery = true)
    Set<Message> getAllMessagesFromThisChat(Long chatId);
}
