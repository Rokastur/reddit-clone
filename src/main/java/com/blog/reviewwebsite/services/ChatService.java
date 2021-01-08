package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.entities.Chat;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.repositories.ChatRepository;
import com.blog.reviewwebsite.wrapper.ChatUsers;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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
            User user = userService.getUserEntity(userId);
            user.addChatSet(chat);
        }
        User init = userService.getUserEntity(initiator.getId());
        init.addChatSet(chat);
        chatRepository.save(chat);
    }

    //TODO can be done with a single query
    public Set<Chat> getAllUserChats(User user) {
        chatRepository.getChatsWithChattersInitialized();
        return chatRepository.getAllThisUserChats(user.getId());
    }

    public void throwExceptionIfUserNotPartOfTheChat(User dbUser, Chat chat) {
        if (!chat.getChatters().contains(dbUser)) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        }
    }
}
