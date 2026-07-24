package com.mu9983.service.impl;

import com.mu9983.service.FileService;
import com.mu9983.utils.MinioUtils;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private MinioUtils minioUtils;

    /**
     * 上传文件
     * @param file 文件地址
     * @param bucketName 桶名
     * @param objectName 文件名
     * @return 文件签名
     */
    @Override
    public String upload(MultipartFile file, String bucketName, String objectName) throws Exception {
        if (!minioUtils.bucketExits(bucketName)) {
            minioUtils.makeBucket(bucketName);
        }
        minioUtils.putObject(file, bucketName, objectName);
        return minioUtils.getPresignedObjectUrl(bucketName, objectName, Method.GET, 3);
    }

}
