package com.mu9983.service.impl;

import com.mu9983.utils.LoadUtils;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentIngestionService {

    @Autowired
    private EmbeddingModel embeddingModel;
    @Autowired
    private EmbeddingStore<TextSegment> embeddingStore;

    /**
     * 上传文件切片存入milvus
     * @param url minio文件的url
     */
    public void ingestFromUrl(String url){
        Document document = LoadUtils.autoLoader(url);
        DocumentSplitter splitter = DocumentSplitters.recursive(100, 0);
        List<TextSegment> segments = splitter.split(document);
        List<Embedding> content = embeddingModel.embedAll(segments).content();
        embeddingStore.addAll(content, segments);
    }

}
