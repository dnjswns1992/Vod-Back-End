package com.example.StreamCraft.dto.Commuity;

import com.example.StreamCraft.Entity.communitypost.CommentEntity;

import java.util.List;
import java.util.Optional;
/**
 * 게시글 + 댓글 목록을 함께 응답하는 DTO
 * - 게시글 상세 페이지에서 댓글까지 함께 전달할 때 사용
 *
 * @param dto 게시글 정보 DTO
 * @param commentEntities 댓글 목록 (없을 수도 있으므로 Optional)
 */

public record PostWithDto(PostResponseDto dto, Optional<List<CommentEntity>> commentEntities) {

}

