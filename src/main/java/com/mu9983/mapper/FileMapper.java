package com.mu9983.mapper;

import com.mu9983.entity.Document;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FileMapper {

    void insertFile(Document document);

    Integer selectFileByFullName(@Param("objectName") String objectName);

    List<Document> selectFileByKey(@Param("key") String key);

    void updateFile(@Param("fileId") int id, @Param("userId") int userId);

}
