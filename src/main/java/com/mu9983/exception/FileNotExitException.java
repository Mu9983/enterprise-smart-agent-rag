package com.mu9983.exception;

public class FileNotExitException extends RuntimeException {
    public FileNotExitException(String message) {
        super(message);
    }
}
