package com.github.Viktor2308.repository;

import com.github.Viktor2308.entity.VideoInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoInfoRepository extends JpaRepository<VideoInfo, Long> {
    List<VideoInfo> findAllByAuthor(String author);

    List<VideoInfo> findAllByWriteIsFalse();

    long countByAuthorAndWriteIsFalse(String author);

    boolean existsByVideoSize(long videoSize);

    boolean existsByVideoName(String videoName);
}
