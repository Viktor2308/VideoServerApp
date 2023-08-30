package com.github.Viktor2308.service;

import com.github.Viktor2308.entity.VideoInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoService {
    VideoInfo createVideo(MultipartFile video) throws IOException;
    

    List<VideoInfo> getAllMyVideo();

    Resource getVideoResourceById(long id);
}
