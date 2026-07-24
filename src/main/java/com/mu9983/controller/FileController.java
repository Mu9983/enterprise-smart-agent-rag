package com.mu9983.controller;

import com.mu9983.entity.Result;
import com.mu9983.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequestMapping("/file")
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("bucketName") String bucketName,
                         @RequestParam("objectName") String objectName) throws Exception {
        if (file == null || file.isEmpty()) {
            return Result.error("file is empty");
        }
        String upload = fileService.upload(file, bucketName, objectName);
        return Result.success(upload);
    }

}
