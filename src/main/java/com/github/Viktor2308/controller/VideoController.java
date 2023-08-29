package com.github.Viktor2308.controller;

import com.github.Viktor2308.entity.VideoInfo;
import com.github.Viktor2308.service.VideoService;
import com.github.Viktor2308.util.FileUtil;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.ResponseEntity.badRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Slf4j
public class VideoController {

    @Value("${server.compression.mime-types}")
    private final List<String> contentVideos;
    private final VideoService videoService;
    private final FileUtil fileUtil;

    @PostMapping(value = "/video/upload/",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> uploadVideo(@RequestPart("content") @NotNull MultipartFile video) throws IOException {
        String videoType = fileUtil.getFileExtension(video);

        if (!contentVideos.contains("video/" + videoType)) {
            log.info("This video type not supported: {}", videoType);
            return badRequest().body("This file isn't a video.");
        }

        VideoInfo info = videoService.createVideo(video);
        return ResponseEntity.ok(info);
    }

    @GetMapping(value = "/video/download/{id}",
            produces = {MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> downloadVideo(@PathVariable("id") @NotEmpty long id) {
        return videoService.getVideoById(id);
    }
}
