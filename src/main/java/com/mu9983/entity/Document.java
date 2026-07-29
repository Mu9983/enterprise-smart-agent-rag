package com.mu9983.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 文档实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    private Integer id;                     // 文档ID
    private String fileName;             // 文件名
    private String fileSuffix;           // 文件后缀
    private Long fileSize;               // 文件大小
    private String minioPath;            // MinIO文件存储路径/对象key
    private Integer uploadUserId;           // 上传人ID
    private String processStatus;        // 处理状态：待解析/向量化中/完成/失败
    private LocalDateTime createTime;    // 创建时间
    private LocalDateTime updateTime;    // 更新时间
    private Integer updateUserId;        // 更新用户Id

    public Document(String fileName, String fileSuffix, Long fileSize, String minioPath, Integer uploadUserId, String processStatus) {
        this.fileName = fileName;
        this.fileSuffix = fileSuffix;
        this.fileSize = fileSize;
        this.minioPath = minioPath;
        this.uploadUserId = uploadUserId;
        this.processStatus = processStatus;
    }
}
