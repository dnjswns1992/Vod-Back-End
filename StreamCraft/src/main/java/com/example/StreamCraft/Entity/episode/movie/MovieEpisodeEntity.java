package com.example.StreamCraft.Entity.episode.movie;

import com.example.StreamCraft.Entity.Video.UploadMainTitleEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class MovieEpisodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long episodeId;

    private String imageUrl;

    private String description;

    private String episodeNumber;

    private String genre;
    private String subtitleUrl;
    private String videoUrl;
    private String videoType;

    @CreationTimestamp
    private LocalDateTime registrationTime;


    @ManyToOne
    @JoinColumn(name = "uploadMainTitleEntityId")
    @JsonBackReference
    private UploadMainTitleEntity uploadMainTitleEntity;


    @Builder
    public MovieEpisodeEntity(String imageUrl, String description, String episodeNumber,
                              String genre, String videoUrl, UploadMainTitleEntity entity, String subtitleUrl, String videoType) {
        this.imageUrl = imageUrl;
        this.description = description;
        this.episodeNumber =episodeNumber;
        this.genre = genre;
        this.videoUrl = videoUrl;
        this.uploadMainTitleEntity = entity;
        this.subtitleUrl = subtitleUrl;
        this.videoType = videoType;
    }

    public MovieEpisodeEntity() {

    }
}
