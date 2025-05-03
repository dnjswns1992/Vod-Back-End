package com.example.StreamCraft.Entity.Video;

import com.example.StreamCraft.Entity.episode.animation.AnimationEpisodeEntity;
import com.example.StreamCraft.Entity.episode.movie.MovieEpisodeEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class
UploadMainTitleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uploadMainTitleEntityId;
    private String title;
    @Column(length =  5000)
    private String mainTitleDescription;
    private String season;
    private String category;
    private String ImageUrl;
    private String MainTitleImageUrl;
    private String genre;
    @CreationTimestamp
    private LocalDateTime RegistrationTime;




    @OneToMany(mappedBy = "uploadMainTitleEntity" , cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AnimationEpisodeEntity> animationEpisodeEntities = new ArrayList<>();

    @OneToMany(mappedBy = "uploadMainTitleEntity", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MovieEpisodeEntity> movieEpisodeEntities = new ArrayList<>();



}
