package com.example.springsecurity04.Table.EpsidoeEntity;

import com.example.springsecurity04.Table.Video.UploadMainTitleEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;

import javax.persistence.*;
@Entity

public class DramaEpisodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int episodeId;
    private String episodeNumber;
    private String ImageUrl;
    private String videoUrl;
    private String description;
    private String genre;
    @ManyToOne
    @JoinColumn(name = "uploadMainTitleEntityId")
    @JsonBackReference
    private UploadMainTitleEntity uploadMainTitleEntity;

    @Builder
    public DramaEpisodeEntity(String episodeNumber, String videoUrl, String description, UploadMainTitleEntity uploadMainTitleEntity,String ImageUrl
    ,String genre){
        this.episodeNumber = episodeNumber;
        this.videoUrl = videoUrl;
        this.description = description;
        this.uploadMainTitleEntity = uploadMainTitleEntity;
        this.ImageUrl = ImageUrl;
        this.genre = genre;
    }
    public DramaEpisodeEntity(){

    }
}
