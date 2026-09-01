package com.mu9983.utils;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.UrlDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;

/**
 * 文件加载类
 */
public class LoadUtils {

    public static Document autoLoader(String url) {
        if (url.endsWith(".txt")) {
            return UrlDocumentLoader.load(url, new TextDocumentParser());
        } else if (url.endsWith(".pdf")) {
            return UrlDocumentLoader.load(url, new ApachePdfBoxDocumentParser());
        } else {
            return UrlDocumentLoader.load(url, new ApacheTikaDocumentParser());
        }
    }

}
