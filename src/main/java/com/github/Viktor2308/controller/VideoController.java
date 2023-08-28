package com.github.Viktor2308.controller;

import com.github.Viktor2308.entity.VideoInfo;
import com.github.Viktor2308.service.VideoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Validated
public class VideoController {

    private final VideoService videoService;

    @PostMapping(value = "/upload/video",
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<VideoInfo> uploadVideo(@RequestPart("content")@Valid @NotEmpty @NotNull MultipartFile video) throws IOException {
        VideoInfo info = videoService.createVideo(video);
        return ResponseEntity.ok(info);
    }
}
