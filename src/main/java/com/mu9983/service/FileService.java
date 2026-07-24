package com.mu9983.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FileService {

    String upload(MultipartFile file, String bucketName, String objectName) throws Exception;

    List<Map<String, Object>> listObjects(String bucketName) throws Exception;
}
