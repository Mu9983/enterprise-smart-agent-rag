package com.mu9983.service.impl;

import com.mu9983.entity.Document;
import com.mu9983.mapper.FileMapper;
import com.mu9983.service.FileService;
import com.mu9983.service.UserService;
import com.mu9983.utils.MinioUtils;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private MinioUtils minioUtils;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private UserService userService;


    private static final String KK_URL = "http://192.168.75.128:8012/onlinePreview";

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
        // 将二进制文件存入minio
        String fileName = objectName.substring(0, objectName.lastIndexOf("."))
                + objectName.substring(objectName.lastIndexOf("."));
        minioUtils.putObject(file, bucketName, fileName);
        // 将文件签名存入mysql
        String fileSuffix = Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf("."));
        Document document = new Document(objectName, fileSuffix, file.getSize()
                , bucketName + "/" + objectName
                , userService.currentUser().getId(), "done");
        fileMapper.insertFile(document);
        return minioUtils.getPresignedObjectUrl(bucketName, fileName, Method.GET, 3);
    }

    /**
     * 文件列表
     *
     * @param bucketName 桶名
     * @return 文件列表
     */
    @Override
    public List<Map<String, Object>> listObjects(String bucketName){
        return minioUtils.listObjects(bucketName);
    }

    /**
     * 文件删除
     * @param bucketName 桶名
     * @param objectName 文件名
     * @return 删除成功与否
     */
    @Override
    public boolean delete(String bucketName, String objectName) {
        try {
            if (!minioUtils.bucketExits(bucketName)) {
                throw new Exception();
            }
            fileMapper.updateFile(fileMapper.selectFileByFullName(objectName), userService.currentUser().getId());
            minioUtils.deleteObject(bucketName, objectName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 文件查询
     * @param key 关键词
     * @return 文件查询结果
     */
    @Override
    public List<Document> search(String key) {
        return fileMapper.selectFileByKey(key);
    }

    /**
     * 文件预览
     *
     * @param bucketName 桶名
     * @param objectName 文件名
     * @return 文件kk预览链接
     */
    @Override
    public String preview(String bucketName, String objectName, String fileSuffix) throws Exception {
        String presignedURL = minioUtils.getPresignedObjectUrl(bucketName, objectName, Method.GET, 60);
        String encodeUrl = Base64.getEncoder().encodeToString(presignedURL.getBytes(StandardCharsets.UTF_8));
        return KK_URL + "?url=" + encodeUrl;

    }


}
