package com.ecommerce.emarket.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {
    public String uploaImage(String path, MultipartFile file) throws IOException {
        // Get Current
        String originalFileName = file.getOriginalFilename();
        // return originalFileName;

        // Generate a unique file name
        String randomId = UUID.randomUUID().toString();
        if (originalFileName == null) {
            throw new IOException("Original file name is null");
        }
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
        // Why File.separator not just / or \ ? Because it is platform independent
        String filePath = path + File.separator + fileName;

        // Check if path exists or create it
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));

        // // Return the file name
        return fileName;
    }
}
