package com.github.Viktor2308.util.impl;

import com.github.Viktor2308.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileUtilImpl implements FileUtil {
       public String homeDirectory() {
        return System.getProperty("user.home");
    }

    @Override
    public String createFile(MultipartFile file) throws IOException {
        String path = new StringBuilder(homeDirectory())
                .append(File.separator)
                .append("videos").append(File.separator)
                .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))).toString();

        String fileName = String.format(
                "%s.%s",
                UUID.randomUUID(),
                FilenameUtils.getExtension(file.getOriginalFilename())
        );

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String location = new StringBuilder(path)
                .append(File.separator)
                .append(fileName)
                .toString();

        Path rezultPath = Paths.get(location);

        Files.write(rezultPath, file.getBytes());

        return location;
    }

    @Override
    public byte[] getFile(String path) throws IOException {
        File file = new File(path);
        byte[] bytes = null;
        if (file.exists()) {
            bytes = FileUtils.readFileToByteArray(file);
        }
        return bytes;
    }

    @Override
    public String getFileName(MultipartFile file) {
        return Paths.get(Objects.requireNonNull(file.getOriginalFilename())).getFileName().toString();
    }

    @Override
    public String getFileExtension(MultipartFile file) {
        return FilenameUtils.getExtension(getFileName(file));
    }


}
