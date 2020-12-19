package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.repositories.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
}
