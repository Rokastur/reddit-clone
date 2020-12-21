package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.entities.Chat;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.repositories.ChatRepository;
import com.blog.reviewwebsite.wrapper.ChatUsers;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class ChatService {

    private ChatRepository chatRepository;
    private UserService userService;

    public ChatService(ChatRepository chatRepository, UserService userService) {
        this.chatRepository = chatRepository;
        this.userService = userService;
    }

    public Chat getChat(Long id) {
        return chatRepository.getOne(id);
    }

    public void createNewChat(User initiator, ChatUsers chatUsersWrapper) {
        Chat chat = new Chat();
        for (Long userId : chatUsersWrapper.getUserIdList()) {
            User user = userService.getUser(userId);
            user.addChatSet(chat);
        }
        User init = userService.getUser(initiator.getId());
        init.addChatSet(chat);
        chatRepository.save(chat);
    }

    public Set<Chat> getAllUserChats(User user) {
        return chatRepository.getAllThisUserChats(user.getId());
    }
}
