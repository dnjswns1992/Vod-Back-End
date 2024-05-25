package com.example.springsecurity04.Dto.Video;

import lombok.Builder;
import lombok.Data;

@Data
public class UploadMainTitleDto {
    private int uploadMainTitleEntityId;

    private String title;
    private String season;
    private String category;
    private String ImageUrl;
    private String genre;


    @Builder
    public UploadMainTitleDto(int uploadMainTitleEntityId,String title,String season,String category,String ImageUrl,String genre) {
        this.uploadMainTitleEntityId = uploadMainTitleEntityId;
        this.season = season;
        this.title = title;
        this.category = category;
        this.ImageUrl = ImageUrl;
        this.genre = genre;
    }
}
