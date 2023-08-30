package com.github.Viktor2308.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VideoInfoDto {
    private String author;
    private String videoName;
    private String percentUpload;
}
