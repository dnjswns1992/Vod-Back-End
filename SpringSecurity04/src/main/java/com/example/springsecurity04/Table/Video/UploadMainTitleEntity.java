package com.example.springsecurity04.Table.Video;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(mappedBy = "uploadMainTitleEntity")
    @JsonManagedReference
    private List<EpisodeEntity> episodeEntityList = new ArrayList<>();

}
