package com.mu9983.service.impl;

import com.mu9983.utils.LoadUtils;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import io.milvus.client.MilvusServiceClient;
import io.milvus.param.dml.DeleteParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mu9983.config.RAGConfig.COLLECTION_NAME;

@Service
public class DocumentServiceImpl {

    @Autowired
    private EmbeddingModel embeddingModel;
    @Autowired
    private EmbeddingStore<TextSegment> embeddingStore;
    @Autowired
    private MilvusServiceClient milvusServiceClient;

    /**
     * 上传文件切片存入milvus
     * @param url minio文件的url
     */
    public void ingestFromUrl(String url, String documentName, Integer userId){
        Document document = LoadUtils.autoLoader(url);
        Metadata meta = document.metadata();
        meta.put("doc_name", documentName);
        meta.put("user_id", userId);
        meta.put("upload_time", System.currentTimeMillis());
        DocumentSplitter splitter = DocumentSplitters.recursive(100, 0);
        List<TextSegment> segments = splitter.split(document);
        List<Embedding> content = embeddingModel.embedAll(segments).content();
        embeddingStore.addAll(content, segments);
    }

    /**
     * 删除milvus中的数据
     * @param documentName 文件名
     */
    public void deleteDocument(String documentName){
        milvusServiceClient.delete(DeleteParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withExpr("metadata[\"doc_name\"] == \"" + documentName+ "\"")
                .build());
    }

}
