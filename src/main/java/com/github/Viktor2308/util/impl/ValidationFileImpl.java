package com.github.Viktor2308.util.impl;

import com.github.Viktor2308.repository.VideoInfoRepository;
import com.github.Viktor2308.util.ValidationFile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class ValidationFileImpl implements ValidationFile {

    private final VideoInfoRepository videoInfoRepository;


    @Override
    public boolean validationFileAlreadyExist(MultipartFile file) {
        long fileSize = file.getSize();
        String fileName = file.getOriginalFilename();
        return videoInfoRepository.existsByVideoSize(fileSize) && videoInfoRepository.existsByVideoName(fileName);
    }
}
