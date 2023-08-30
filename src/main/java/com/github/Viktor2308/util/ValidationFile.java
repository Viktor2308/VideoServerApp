package com.github.Viktor2308.util;

import org.springframework.web.multipart.MultipartFile;

public interface ValidationFile {
    boolean validationFileAlreadyExist(MultipartFile file);
}
