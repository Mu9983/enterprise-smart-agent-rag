package com.mu9983.mapper;

import com.mu9983.entity.Document;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FileMapper {

    void insertFile(Document document);

    int selectFile(@Param("objectName") String objectName);

    void updateFile(@Param("fileId") int id, @Param("userId") int userId);

}
