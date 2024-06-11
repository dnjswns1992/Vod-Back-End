package com.example.springsecurity04.Dto.Video;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.net.URL;

@Data
public class VideoDto {

    private int VideoId;
    private String title;
    private String episodeNumber;
    private String ImageUrl;
    @JsonProperty("videoUrl")
    private String videoUrl;
    private String description;
    private String genre;
    @JsonProperty("videoType")
    private String videoType;
}
