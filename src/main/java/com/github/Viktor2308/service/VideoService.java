package com.github.Viktor2308.service;

import com.github.Viktor2308.entity.VideoInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface VideoService {
    VideoInfo createVideo(MultipartFile video) throws IOException;

    ResponseEntity<?> getVideoById(long id);
}
