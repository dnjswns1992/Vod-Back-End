package com.example.springsecurity04.Table.Video;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class EpisodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int episodeId;
    private String episodeNumber;
    private String videoUrl;
    private String description;
    @ManyToOne
    @JoinColumn(name = "uploadMainTitleEntityId")
    @JsonBackReference
    private UploadMainTitleEntity uploadMainTitleEntity;

     @Builder
    public EpisodeEntity(String episodeNumber,String videoUrl,String description,UploadMainTitleEntity uploadMainTitleEntity){
        this.episodeNumber = episodeNumber;
        this.videoUrl = videoUrl;
        this.description = description;
        this.uploadMainTitleEntity = uploadMainTitleEntity;
    }
    public EpisodeEntity(){

    }
}
