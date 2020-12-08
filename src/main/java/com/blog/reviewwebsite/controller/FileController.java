package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.services.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/uploadFile")
    public String submitProfilePicture(@RequestParam("file") MultipartFile file, @RequestParam Long id) throws IOException {
        fileService.storeFile(file, id);
        return "redirect:/user/user/" + id;
    }
}
