package com.github.Viktor2308.exception;

public class UploadLimitException extends RuntimeException{
    public UploadLimitException(String message) {
        super(message);
    }
}
