package com.mu9983.service;

import com.mu9983.entity.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FileService {

    String upload(MultipartFile file, String bucketName, String objectName) throws Exception;

    List<Map<String, Object>> listObjects(String bucketName) throws Exception;

    boolean delete(String bucketName, String objectName);

    List<Document> search(String key);

    String preview(String bucketName, String objectName, String fileSuffix) throws Exception;

    List<Map<String, Object>> listBuckets();

    String download(String bucketName, String objectName) throws Exception;

    void makeBucket(String bucketName) throws Exception;
}
