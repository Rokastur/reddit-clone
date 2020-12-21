package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.entities.Message;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.services.ChatService;
import com.blog.reviewwebsite.services.MessageService;
import com.blog.reviewwebsite.services.UserService;
import com.blog.reviewwebsite.wrapper.ChatUsers;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private ChatService chatService;
    private UserService userService;
    private MessageService messageService;

    public ChatController(ChatService chatService, UserService userService, MessageService messageService) {
        this.chatService = chatService;
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping
    public String getChats(Model model, @AuthenticationPrincipal User user) {
        ChatUsers UserIdWrapper = new ChatUsers();
        model.addAttribute("UserIdWrapper", UserIdWrapper);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("chatrooms", chatService.getAllUserChats(user));

        return "chat";
    }

    @PostMapping("/submit")
    public String newChat(@AuthenticationPrincipal User user, @ModelAttribute ChatUsers UserIdWrapper) {
        chatService.createNewChat(user, UserIdWrapper);
        return "redirect:/chat";
    }

    @GetMapping("/{id}")
    public String getChat(Model model, @PathVariable Long id) {
        model.addAttribute("messages", messageService.displayAllChatsMessages(chatService.getChat(id)));
        model.addAttribute("newMessage", new Message());
        model.addAttribute("id", id);
        return "messaging";
    }

    @PostMapping("/{id}/message/submit")
    public String submitMessage(@ModelAttribute Message message, @PathVariable Long id, @AuthenticationPrincipal User user) {
        messageService.sendNewMessage(chatService.getChat(id), user, message);
        return "redirect:/chat/" + id;
    }
}
