package com.github.Viktor2308.service.impl;

import com.github.Viktor2308.dto.VideoInfoDto;
import com.github.Viktor2308.repository.VideoInfoRepository;
import com.github.Viktor2308.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final VideoInfoRepository videoInfoRepository;

    @Override
    public List<VideoInfoDto> getAllCurrentUpload() {
        return videoInfoRepository.findAllByWriteIsFalse().stream()
                .map(videoInfo ->
                        VideoInfoDto.builder()
                                .author(videoInfo.getAuthor())
                                .videoName(videoInfo.getVideoName())
                                .build())
                .toList();
    }
}
