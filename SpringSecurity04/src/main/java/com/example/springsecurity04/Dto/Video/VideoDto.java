package com.example.springsecurity04.Dto.Video;

import lombok.Data;

import java.net.URL;

@Data
public class VideoDto {

    private int VideoId;
    private String title;
    private String episodeNumber;
    private URL ImageUrl;
    private URL VideoUrl;
    private String description;
    private String genre;
    ;
}
