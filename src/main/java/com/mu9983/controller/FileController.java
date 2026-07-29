package com.mu9983.controller;

import com.mu9983.entity.Document;
import com.mu9983.entity.Result;
import com.mu9983.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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
        log.info("文件上传");
        if (file == null || file.isEmpty()) {
            log.error("文件为空");
            return Result.error("file is empty");
        }
        String upload = fileService.upload(file, bucketName, objectName);
        return Result.success(upload);
    }

    @GetMapping("/list")
    public Result listFile(@RequestParam("bucketName") String bucketName) throws Exception {
        log.info("文件列表");
        List<Map<String, Object>> list = fileService.listObjects(bucketName);
        return Result.success(list);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam("bucketName") String bucketName,
                         @RequestParam("objectName") String objectName) {
        log.info("文件删除");
        boolean isDeleted = fileService.delete(bucketName, objectName);
        if (isDeleted) {
            log.info("文件删除成功");
            return Result.success("文件删除成功");
        }
        else {
            log.error("文件删除失败");
            return Result.error("文件删除失败");
        }
    }

    @GetMapping("/search")
    public Result search(@RequestParam("key") String key) {
        log.info("搜索文件");
        List<Document> search = fileService.search(key);
        if (search != null && !search.isEmpty()) {
            return Result.success(search);
        }
        return Result.success("搜索结果为空");
    }

    @GetMapping("/preview")
    public ResponseEntity<?> preview(@RequestParam String fileName, HttpServletResponse response) {
        log.info("预览文件");
        Document document = fileService.search(fileName).get(0);
        try {
            String bucketName = document.getMinioPath().split("/")[0];
            response.sendRedirect(fileService.preview(bucketName, fileName, document.getFileSuffix()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("预览失败，文件损坏或服务异常");
        }
        return null;
    }
}
