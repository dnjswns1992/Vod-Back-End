package com.example.springsecurity04.Table.EpsidoeEntity;

import com.example.springsecurity04.Table.Video.UploadMainTitleEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class AnimationEpisodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long episodeId;

    private String imageUrl;

    private String description;

    private String episodeNumber;

    private String genre;
    private String subtitleUrl;
    private String videoUrl;
    @CreationTimestamp
    private LocalDateTime registrationTime;


    @ManyToOne
    @JoinColumn(name = "uploadMainTitleEntityId")
    @JsonBackReference
    private UploadMainTitleEntity uploadMainTitleEntity;


    @Builder
    public AnimationEpisodeEntity(String imageUrl,String description,String episodeNumber,
                                  String genre,String videoUrl,UploadMainTitleEntity entity,String subtitleUrl) {
        this.imageUrl = imageUrl;
        this.description = description;
        this.episodeNumber =episodeNumber;
        this.genre = genre;
        this.videoUrl = videoUrl;
        this.uploadMainTitleEntity = entity;
        this.subtitleUrl = subtitleUrl;
    }

    public AnimationEpisodeEntity() {

    }
}
