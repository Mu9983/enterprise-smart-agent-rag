package com.mu9983.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Bean
    public MinioClient minioClient() {
        // 构建MinioClient对象
        return MinioClient.builder()
                .endpoint("http://192.168.75.128:9000")
                .credentials("minioadmin", "minioadmin")
                .build();
    }
}
