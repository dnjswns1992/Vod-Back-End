package com.example.springsecurity04.Table.Video;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.net.URL;

@Entity
public class VideoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int VideoId;
    private String title;
    private String content;

    private String category;

    private String ImageUrl;
    private String VideoUrl;
@Builder
    public VideoEntity(String title,String content,String category,String ImageUrl,String videoUrl) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.ImageUrl = ImageUrl;
        this.VideoUrl = videoUrl;
    }

    public VideoEntity() {

    }
}
