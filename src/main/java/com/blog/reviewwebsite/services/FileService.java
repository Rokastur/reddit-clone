package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.entities.File;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.repositories.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Service
public class FileService {

    private FileRepository fileRepository;
    private UserService userService;

    public FileService(FileRepository fileRepository, UserService userService) {
        this.fileRepository = fileRepository;
        this.userService = userService;
    }

    @Transactional
    public File storeFile(MultipartFile mpFile, Long userId) throws IOException {
        deleteFileIfUserHasOne(userId);
        String filename = StringUtils.cleanPath(mpFile.getOriginalFilename());
        File file = new File();
        file.setName(filename);
        file.setType(mpFile.getContentType());
        file.setData(mpFile.getBytes());

        User user = userService.getUser(userId);
        user.addFile(file);
        return fileRepository.save(file);
    }

    public void deleteFileIfUserHasOne(Long userId) {
        Optional<File> file = Optional.ofNullable(getFileByUserId(userId));
        file.ifPresent(this::deleteFile);
    }

    @Transactional
    public File getFileByUserId(Long userId) {
        return fileRepository.getUserFile(userId);
    }

    public String retrieveImageEncodedInBase64(File file) {
        byte[] base64Bytes = Base64.getEncoder().encode(file.getData());
        return new String(base64Bytes, StandardCharsets.UTF_8);
    }

    public void deleteFile(File file) {
        fileRepository.delete(file);
    }

    public String getUsersProfilePictureConvertedToString(User user) {
        if (userService.userHasFile(user)) {
            File file = fileRepository.getUserFile(user.getId());
            return retrieveImageEncodedInBase64(file);
        }
        return null;
    }
}
