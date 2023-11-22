package com.intervlgo.ourfolio.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        String newFileName = generateUniqueFileName(fileName);

        File saveFile = new File(projectPath, newFileName);

        file.transferTo(saveFile);

        return newFileName;
    }

    public Resource downloadFile(String fileName) throws IOException {
        // return Resource
       return null;
    }

    private String generateUniqueFileName(String originalFilename) {
        String extension = StringUtils.getFilenameExtension(originalFilename);
        return UUID.randomUUID().toString() + "." + extension;
    }
}