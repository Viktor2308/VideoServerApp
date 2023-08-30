package com.github.Viktor2308.controller;

import com.github.Viktor2308.dto.VideoInfoDto;
import com.github.Viktor2308.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AdminController {

    private final AdminService adminService;
    @Operation(summary = "Get all current upload videos")
    @ApiResponse(            responseCode = "200",
            description = "OK",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = VideoInfoDto.class))
    )
    @GetMapping(value = "/admin")
    public List<VideoInfoDto> getAllCurrentUpload(){
        return adminService.getAllCurrentUpload();
    }
}
