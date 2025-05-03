package com.example.StreamCraft.dto.Commuity;

import lombok.Data;

import java.time.LocalDate;

/**
 * 댓글 조회/응답용 DTO
 */
@Data
public class CommentResponseDto {

    private int commentId;  // 댓글 고유 ID
    private String nickname;  // 댓글 작성자 닉네임
    private String title;   // 댓글이 달린 게시글 제목
    private String content; // 댓글 본문 내용
    private LocalDate createAt;  // 작성일
    private int postId; // 연결된 게시글 ID


}
