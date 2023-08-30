package com.github.Viktor2308.service.impl;

import com.github.Viktor2308.entity.VideoInfo;
import com.github.Viktor2308.exception.DuplicateVideoException;
import com.github.Viktor2308.exception.NotFoundVideoException;
import com.github.Viktor2308.repository.VideoInfoRepository;
import com.github.Viktor2308.service.UserService;
import com.github.Viktor2308.service.VideoService;
import com.github.Viktor2308.util.FileUtil;
import com.github.Viktor2308.util.ValidationFile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class VideoServiceImpl implements VideoService {

    private final FileUtil fileUtil;
    private final VideoInfoRepository repository;
    private final UserService userService;
    private final ValidationFile validationFile;

    @Override
    @Transactional
    public VideoInfo createVideo(MultipartFile video) throws IOException {

        if(validationFile.validationFileAlreadyExist(video)){
            log.error("Error: Video is already exist!");
            throw new DuplicateVideoException("Video is already exist!");
        }

        VideoInfo info = VideoInfo.builder()
                .videoName(fileUtil.getFileName(video))
                .author(userService.getCurrentUsername())
                .videoExtension(fileUtil.getFileExtension(video))
                .videoSize(video.getSize())
                .write(false)
                .crated(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        String path = fileUtil.createFile(video);
        info.setVideoPath(path);
        info.setWrite(true);
        return repository.save(info);
    }

    @Override
    public Resource getVideoResourceById(long id) {
        VideoInfo info = repository.findById(id).orElseThrow(() ->
                new NotFoundVideoException("Video not found with id: " + id));

        if (!info.isWrite()) {
            log.error("Error: File is not upload!");
            throw new RuntimeException("File is not upload!");
        }

        Path video = Path.of(info.getVideoPath());
        try {
            Resource resource = new UrlResource(video.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.error("Error: Could not read the file!");
                throw new RuntimeException("Could not read the file.");
            }
        } catch (MalformedURLException e) {
            log.error("Error: {}", e.getMessage());
            throw new RuntimeException("Error:" + e.getMessage());
        }
    }

    @Override
    public List<VideoInfo> getAllMyVideo() {
        String author = userService.getCurrentUsername();
        return repository.findAllByAuthor(author);
    }
}
