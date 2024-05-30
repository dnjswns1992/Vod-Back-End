package com.example.springsecurity04.Dto.Video.Animation;

import com.example.springsecurity04.Table.EpsidoeEntity.AnimationEpisodeEntity;
import com.example.springsecurity04.Table.Video.UploadMainTitleEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class AnimationEpisodeEntityDto {
    private List<AnimationEpisodeEntity> episode;
    private UploadMainTitleEntity uploadMainTitleEntity;

}
