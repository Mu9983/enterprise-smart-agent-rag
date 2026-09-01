package com.mu9983.config;

import io.milvus.client.MilvusServiceClient;
import io.milvus.param.ConnectParam;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class MilvusConfig {

    @Bean
    public MilvusServiceClient milvusServiceClient() {
        ConnectParam connectParam = ConnectParam.newBuilder()
                .withUri("http://192.168.75.128:19530")
                .withConnectTimeout(1000, TimeUnit.MILLISECONDS)
                .build();
        return new MilvusServiceClient(connectParam);
    }


}
