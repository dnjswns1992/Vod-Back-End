package com.example.StreamCraft.dto.media.animation;

import com.example.StreamCraft.Entity.episode.animation.AnimationEpisodeEntity;
import com.example.StreamCraft.Entity.Video.UploadMainTitleEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 애니메이션 에피소드 조회 응답 DTO
 * - 메인 타이틀과 에피소드 리스트를 함께 전달
 */
@Setter
@Getter
public class AnimationEpisodeResponseDto {
    private List<AnimationEpisodeEntity> episode;  // 에피소드 목록
    private UploadMainTitleEntity uploadMainTitleEntity;  // 메인 타이틀 정보

}
