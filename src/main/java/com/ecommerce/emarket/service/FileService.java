package com.ecommerce.emarket.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public String uploaImage(String path, MultipartFile file) throws IOException;
}