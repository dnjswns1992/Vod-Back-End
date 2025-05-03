package com.example.StreamCraft.dto.media.movie;
import com.example.StreamCraft.Entity.episode.movie.MovieEpisodeEntity;
import com.example.StreamCraft.Entity.Video.UploadMainTitleEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * 영화 에피소드 조회 응답 DTO
 * - 메인 타이틀과 에피소드 리스트를 함께 전달
 */
@Setter
@Getter
public class MovieEpisodeResponseDto {

    private List<MovieEpisodeEntity> episode; // 에피소드 목록
    private UploadMainTitleEntity uploadMainTitleEntity; // 메인 타이틀 정보
}
