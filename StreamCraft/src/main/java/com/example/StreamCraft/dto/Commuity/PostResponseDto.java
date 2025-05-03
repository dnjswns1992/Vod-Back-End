package com.example.StreamCraft.dto.Commuity;


import lombok.Data;

import java.time.LocalDate;
/**
 * 게시글 조회 응답 DTO
 * - 게시글 상세 페이지 등에 사용됨
 */
@Data
public class PostResponseDto {

    private int postId; // 게시글 고유 ID
    private String content; // 게시글 내용
    private String title;  // 게시글 제목
    private String username;  // 작성자 ID
    private int postHit; // 조회 수
    private LocalDate createTime; // 작성 일자
    private String nickname; // 작성자 닉네임
}
