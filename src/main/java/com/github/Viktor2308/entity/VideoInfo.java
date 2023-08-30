package com.github.Viktor2308.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long id;
    @Column(name = "author", nullable = false)
    private String author;
    private String videoName;
    public String videoExtension;
    private String videoPath;
    @JsonIgnore
    private boolean write;
    private long videoSize;
    private Timestamp crated;
}
