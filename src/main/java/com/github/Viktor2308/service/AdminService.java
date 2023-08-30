package com.github.Viktor2308.service;

import com.github.Viktor2308.dto.VideoInfoDto;

import java.util.List;

public interface AdminService {
    List<VideoInfoDto> getAllCurrentUpload();
}
