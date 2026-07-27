package com.mu9983.mapper;

import com.mu9983.entity.Document;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {

    void insertFile(Document document);

}
