package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.entities.Message;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.services.ChatService;
import com.blog.reviewwebsite.services.MessageService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/message")
public class MessagesController {

    private MessageService messageService;
    private ChatService chatService;

    public MessagesController(MessageService messageService, ChatService chatService) {
        this.messageService = messageService;
        this.chatService = chatService;
    }

    @PostMapping("/{id}/submit")
    public String submitMessage(@ModelAttribute Message message, @PathVariable Long id, @AuthenticationPrincipal User user) {
        messageService.sendNewMessage(chatService.getChat(id), user, message);
        return "redirect:/chat/" + id;
    }
}
