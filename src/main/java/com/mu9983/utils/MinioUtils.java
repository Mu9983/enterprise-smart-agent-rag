package com.mu9983.utils;

import io.minio.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

/**
 * minio工具类
 */
@Component
public class MinioUtils {

    private static MinioClient minioClient;

    public static void setMinioClient(MinioClient minioClient) {
        MinioUtils.minioClient = minioClient;
    }

    /**
     * 上传文件
     * @param file 文件地址
     * @param bucketName 桶名
     * @param objectName 文件名
     * @return 文件响应
     */
    public static ObjectWriteResponse putObject(File file, String bucketName, String objectName) throws Exception {
        return minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(new FileInputStream(file), file.length(), -1)
                .build());
    }

    /**
     * 获取文件签名网址
     * @param bucketName 桶名
     * @param objectName 文件名
     * @param method     访问方式
     * @param duration   有效时间（分钟）
     * @return           文件签名
     */
    public static String getPresignedObjectUrl(String bucketName, String objectName, Method method, int duration) throws Exception {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .method(method)
                .expiry(duration, TimeUnit.MINUTES)
                .build());
    }

}
