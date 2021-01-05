package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.entities.Chat;
import com.blog.reviewwebsite.entities.Message;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.repositories.MessageRepository;
import com.blog.reviewwebsite.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public void sendNewMessage(Chat chat, User user, Message message) {
        User dbUser = userRepository.getOne(user.getId());
        chat.addMessage(message);
        dbUser.addMessage(message);
        messageRepository.save(message);
    }

    public Set<Message> displayAllChatsMessages(Chat chat) {
        return messageRepository.getAllMessagesFromThisChat(chat.getId());
    }

}
