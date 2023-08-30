package com.github.Viktor2308.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUtil {
    String createFile(MultipartFile file) throws IOException;

    byte[] getFile(String path) throws IOException;

    String getFileName(MultipartFile file);

    String getFileExtension(MultipartFile file);


}
