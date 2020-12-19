package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.entities.Chat;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.repositories.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ChatService {

    private ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Chat getChat(Long id) {
        return chatRepository.getOne(id);
    }

    public void createNewChat(User initiator, Set<User> recipients) {
        Chat chat = new Chat();
        chat.getChatters().add(initiator);
        for (User user : recipients) {
            chat.getChatters().add(user);
        }
        chatRepository.save(chat);
    }
}
