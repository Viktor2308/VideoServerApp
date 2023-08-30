package com.github.Viktor2308.util.impl;

import com.github.Viktor2308.repository.VideoInfoRepository;
import com.github.Viktor2308.service.UserService;
import com.github.Viktor2308.util.UploadLimiter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UploadLimiterImpl implements UploadLimiter {

    private final VideoInfoRepository repository;
    private final UserService userService;

    @Override
    public boolean isLimitUpload(int countUploadVideoLimit) {
        String currentAuthorName = userService.getCurrentUsername();
        return repository.countByAuthorAndWriteIsFalse(currentAuthorName) > countUploadVideoLimit;
    }
}
