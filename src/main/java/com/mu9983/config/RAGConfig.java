package com.mu9983.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.milvus.MilvusEmbeddingStore;
import io.milvus.client.MilvusServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RAGConfig {

    @Autowired
    private EmbeddingModel embeddingModel;
    @Autowired
    private MilvusServiceClient milvusServiceClient;

    public static final String COLLECTION_NAME = "rag";


    /**
     * 加载文件进milvus官方存储并分割向量化存储
     * @return
     */
    @SuppressWarnings({"all"})
    @Bean
    public EmbeddingStore<TextSegment> store() {
        return MilvusEmbeddingStore.builder()
                .milvusClient(milvusServiceClient)
                .collectionName(COLLECTION_NAME)
                .autoFlushOnInsert(true)
                .dimension(1024)
                .build();
    }

    /**
     * 创建向量数据库检索对象
     * @param store
     * @return
     */
    @SuppressWarnings({"all"})
    @Bean
    public ContentRetriever contentRetriever(EmbeddingStore store) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(store)
                .minScore(0.5)
                .maxResults(10)
                .embeddingModel(embeddingModel)
                .build();
    }

}
