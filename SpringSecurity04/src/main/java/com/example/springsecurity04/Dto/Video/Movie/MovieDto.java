package com.example.springsecurity04.Dto.Video.Movie;
import com.example.springsecurity04.Table.EpsidoeEntity.MovieEpisodeEntity;
import com.example.springsecurity04.Table.Video.UploadMainTitleEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class MovieDto {

    private List<MovieEpisodeEntity> episode;
    private UploadMainTitleEntity uploadMainTitleEntity;
}
