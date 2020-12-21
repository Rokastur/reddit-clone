package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.entities.Chat;
import com.blog.reviewwebsite.entities.Message;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void sendNewMessage(Chat chat, User user, Message message) {
        message.setChat(chat);
        message.setUser(user);
        messageRepository.save(message);
    }

    public Set<Message> displayAllChatsMessages(Chat chat) {
        return messageRepository.getAllMessagesFromThisChat(chat.getId());
    }

}
