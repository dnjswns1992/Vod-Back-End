package com.example.StreamCraft.dto.media.upload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 개별 영상 에피소드 메타데이터 저장 요청 DTO
 * - 영상 상세 정보와 함께 저장
 */
@Data
public class VideoMetadataRequestDto {

    private int VideoId; // 영상 고유 ID
    private String title; // 영상 제목
    private String episodeNumber; // 에피소드 번호
    private String ImageUrl; // 썸네일 이미지 URL
    @JsonProperty("videoUrl")
    private String videoUrl; // 영상 스트리밍 URL
    private String description;  // 설명
    private String genre;  // 장르
    @JsonProperty("videoType")
    private String videoType;  // 영상 타입 (애니메이션/영화 등)

}
