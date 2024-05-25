package com.example.springsecurity04.Table.Video;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class UploadMainTitleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uploadMainTitleEntityId;
    private String title;
    private String season;
    private String category;
    private String ImageUrl;
    private String genre;

}
