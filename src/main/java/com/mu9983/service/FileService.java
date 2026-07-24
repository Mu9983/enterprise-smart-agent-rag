package com.mu9983.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String upload(MultipartFile file, String bucketName, String objectName) throws Exception;

}
