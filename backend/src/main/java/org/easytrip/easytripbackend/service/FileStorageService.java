package org.easytrip.easytripbackend.service;

import org.easytrip.easytripbackend.exception.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
    private static Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    private final Path uploadDir= Paths.get("upload");

    public FileStorageService() {
        try{
            Files.createDirectories(uploadDir);
            logger.info("upload directory initilized at:{}", uploadDir.toAbsolutePath().toString());
        }catch(IOException e){
            logger.error("Failed to create upload directory", e);
            throw new FileStorageException("Could not initialize upload directory");
        }
    }

    public String uploadFile(MultipartFile file, Long userId) {
        if(file.isEmpty()){
            logger.error("Empty file uploaded for userId:{}", userId);
            throw new FileStorageException("Empty file uploaded for userId");
        }
        String fileName = userId+"_"+System.currentTimeMillis()+"_"+file.getOriginalFilename();
        Path  targetPath = uploadDir.resolve(fileName);
        try {
            file.transferTo(targetPath);
            logger.info("File stored successfully: {}", targetPath.toAbsolutePath());
            return targetPath.toString();
        } catch (IOException e) {
            logger.error("Failed to store file: {}", fileName, e);
            throw new FileStorageException("Failed to store file: " + fileName);
        }
    }

    public File getFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            logger.error("File not found: {}", filePath);
            throw new FileStorageException("File not found: " + filePath);
        }
        return file;
    }
}
