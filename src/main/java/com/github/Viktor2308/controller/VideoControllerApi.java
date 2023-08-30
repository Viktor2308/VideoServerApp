package com.github.Viktor2308.controller;

import com.github.Viktor2308.entity.VideoInfo;
import com.github.Viktor2308.exception.FileExtensionException;
import com.github.Viktor2308.exception.UploadLimitException;
import com.github.Viktor2308.service.VideoService;
import com.github.Viktor2308.util.FileUtil;
import com.github.Viktor2308.util.UploadLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Slf4j
public class VideoControllerApi {

    @Value("${server.compression.mime-types}")
    private final List<String> contentVideos;
    @Value("${video.server.app.upload.limit}")
    private int countUploadVideoLimit;

    private final VideoService videoService;
    private final FileUtil fileUtil;
    private final UploadLimiter uploadLimiter;

    @Operation(summary = "Upload video on server")
    @ApiResponse(responseCode = "200",
            description = "OK",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = VideoInfo.class))
    )
    @PostMapping(value = "/video/upload",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public void uploadVideo(@RequestPart("multipartFile") @NotNull MultipartFile video) throws IOException {

        String videoType = fileUtil.getFileExtension(video);

        if (!contentVideos.contains("video/" + videoType)) {
            log.error("Error: This video type not supported: {}", videoType);
            throw new FileExtensionException("This file isn't a video.");
        }

        if (uploadLimiter.isLimitUpload(countUploadVideoLimit)) {
            log.error("Error: Count uploads for the current user has been exceeded: {}", countUploadVideoLimit);
            throw new UploadLimitException("Count uploads for the current user has been exceeded.");
        }

        VideoInfo info = videoService.createVideo(video);
    }

    @Operation(summary = "Download video from server by video id.")
    @GetMapping(value = "/video/download/{id}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadVideo(@PathVariable("id") @NotEmpty long id) {

        Resource video = videoService.getVideoResourceById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=\"" + video.getFilename() + "\"")
                .body(video);
    }
    @Operation(summary = "Get list all videos current user.")
    @ApiResponse(responseCode = "200",
            description = "OK",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = VideoInfo.class))))
    @GetMapping(value = "/video/download/")
    public ResponseEntity<List<VideoInfo>> getAllMyVideo() {
        return ResponseEntity.ok(videoService.getAllMyVideo());
    }
}
