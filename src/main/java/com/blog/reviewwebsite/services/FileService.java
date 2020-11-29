package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.entities.File;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.repositories.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Service
public class FileService {

    private FileRepository fileRepository;
    private UserService userService;

    public FileService(FileRepository fileRepository, UserService userService) {
        this.fileRepository = fileRepository;
        this.userService = userService;
    }

    public File storeFile(MultipartFile mpFile, Long userId) throws IOException {
        String filename = StringUtils.cleanPath(mpFile.getOriginalFilename());
        File file = new File();
        file.setName(filename);
        file.setType(mpFile.getContentType());
        file.setData(mpFile.getBytes());

        User user = userService.getUser(userId);
        file.setUser(user);
        return fileRepository.save(file);
    }

    public File getFile(Long id) {
        return fileRepository.getOne(id);
    }

    @Transactional
    public File getFileByUserId(Long userId) {
        return fileRepository.getUserFile(userId);
    }

    public String retrieveImageEncodedInBase64(File file) throws UnsupportedEncodingException {
        byte[] base64Bytes = Base64.getEncoder().encode(file.getData());
        return new String(base64Bytes, "UTF-8");
    }


    public void deleteFile(File file) {
        fileRepository.delete(file);
    }
}
