package com.mu9983;

import io.minio.*;
import io.minio.http.Method;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class EnterpriseSmartAgentRagApplicationTests {

    @Autowired
    private MinioClient minioClient;

    /**
     * minio相关测试，与业务代码无关
     */
    //@Test
    public void test01() throws Exception {
        boolean isExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket("minio-data").build());
        System.out.println("minio-data is exits?" + isExists);
    }

    //@Test
    public void test02() throws Exception {
        boolean isExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket("minio-data").build());
        if (!isExists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket("minio-data").build());
        }
    }

    //@Test
    public void test03() throws Exception {
        File file = new File("D:\\DevelopingSoftware\\Java project\\Irena.jpg");
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
                .bucket("minio-data")
                .object("test.jpg")
                .stream(new FileInputStream(file), file.length(), -1)
                .build());
        System.out.println(objectWriteResponse);
    }

    //@Test
    public void test04() throws Exception {
        String presignedObjectUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket("minio-data")
                .object("test.jpg")
                .method(Method.GET)
                .expiry(3, TimeUnit.MINUTES)
                .build());
        System.out.println(presignedObjectUrl);
    }

    @Test
    public void test05() throws Exception {

    }

}
