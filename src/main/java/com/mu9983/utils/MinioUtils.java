package com.mu9983.utils;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * minio工具类
 */
@Component
public class MinioUtils {

    @Autowired
    private MinioClient minioClient;

    /**
     * 判断桶是否存在
     * @param bucketName 桶名
     * @return 布尔值
     */
    public boolean bucketExits(String bucketName) throws Exception {
        return minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(bucketName)
                .build());
    }

    /**
     * 创建桶
     * @param bucketName 桶名
     */
    public void makeBucket(String bucketName) throws Exception {
        minioClient.makeBucket(MakeBucketArgs.builder()
                .bucket(bucketName)
                .build());
    }

    /**
     * 上传文件
     * @param file 文件地址
     * @param bucketName 桶名
     * @param objectName 文件名
     */
    public ObjectWriteResponse putObject(MultipartFile file, String bucketName, String objectName) throws Exception {
        return minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .build());
    }

    /**
     * 获取文件签名网址
     *
     * @param bucketName 桶名
     * @param objectName 文件名
     * @param method     访问方式
     * @param duration   有效时间（分钟）
     * @return 文件签名
     */
    public String getPresignedObjectUrl(String bucketName, String objectName, Method method, int duration) throws Exception {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .method(method)
                .expiry(duration, TimeUnit.MINUTES)
                .build());
    }

    /**
     * 遍历桶内文件
     * @param bucketName 桶名
     * @return 字符串
     */
    public List<Map<String, Object>> listObjects(String bucketName) {
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder()
                .bucket(bucketName)
                .recursive(false)
                .build());
        List<Map<String, Object>> list = new ArrayList<>();
        for (Result<Item> result : results) {
            try {
                Item item = result.get();
                Map<String, Object> map = new HashMap<>();
                map.put("fileName", item.objectName());
                map.put("fileSize", item.size());
                map.put("isFolder", item.isDir());
                map.put("updatedTime", item.lastModified());
                list.add(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}
