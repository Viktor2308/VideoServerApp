package com.github.Viktor2308.service.impl;

import com.github.Viktor2308.entity.VideoInfo;
import com.github.Viktor2308.exception.NotFoundVideoException;
import com.github.Viktor2308.repository.VideoInfoRepository;
import com.github.Viktor2308.service.VideoService;
import com.github.Viktor2308.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.springframework.http.ResponseEntity.ok;

@Service
@AllArgsConstructor
@Slf4j
public class VideoServiceImpl implements VideoService {

    private final FileUtil fileUtil;
    private final VideoInfoRepository repository;

    @Override
    @Transactional
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
        info.setWrite(true);
        return repository.save(info);
    }

    @Override
    public ResponseEntity<?> getVideoById(long id) {
        VideoInfo info = repository.findById(id).orElseThrow(() ->
                new NotFoundVideoException("Video not found with id: "+ id));

        if (!info.isWrite()) {
            return new ResponseEntity<>(HttpStatus.LOCKED);
        }

        String path = info.getVideoPath();

        try {
            byte[] video = fileUtil.getFile(path);
            return ok(video);

        } catch (IOException e) {
            log.error("Error download!");
            e.printStackTrace();
        }
        return (ResponseEntity<?>) ok();
    }
}
