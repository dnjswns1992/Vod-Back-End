    package com.example.StreamCraft.dto.media.upload;

    import lombok.Builder;
    import lombok.Data;

    /**
     * 영상(애니/영화 등) 메인 타이틀 업로드 요청 DTO
     */
    @Data
    public class UploadMainTitleRequestDto {
        // 고유 ID (기존 데이터 수정 시 사용)
        private int uploadMainTitleEntityId;
        // 타이틀명
        private String title;
        // 시즌 정보
        private String season;
        // 상세 설명
        private String mainTitleDescription;
        // 카테고리 (애니/영화)
        private String category;
        // 메인 타이틀 썸네일 이미지
        private String MainTitleImageUrl;
        // 일반 썸네일 이미지
        private String ImageUrl;
        // 장르
        private String genre;


        @Builder
        public UploadMainTitleRequestDto(int uploadMainTitleEntityId, String title, String season, String category, String ImageUrl, String genre) {
            this.uploadMainTitleEntityId = uploadMainTitleEntityId;
            this.season = season;
            this.title = title;
            this.category = category;
            this.ImageUrl = ImageUrl;
            this.genre = genre;
        }
    }
