package com.github.Viktor2308.service.impl;

import com.github.Viktor2308.entity.VideoInfo;
import com.github.Viktor2308.repository.VideoInfoRepository;
import com.github.Viktor2308.service.VideoService;
import com.github.Viktor2308.util.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final FileUtil fileUtil;
    private final VideoInfoRepository repository;

    @Override
    public VideoInfo createVideo(MultipartFile video) throws IOException {
        VideoInfo info = VideoInfo.builder()
                .videoName(fileUtil.getFileName(video))
                .videoExtension(fileUtil.getFileExtension(video))
                .videoSize(video.getSize())
                .write(false)
                .crated(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        String path = fileUtil.createFile(video);
        info.setVideoPath(path);
        return repository.save(info);
    }
}
