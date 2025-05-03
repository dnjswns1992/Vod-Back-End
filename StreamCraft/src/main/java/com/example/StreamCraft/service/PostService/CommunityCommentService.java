package com.example.StreamCraft.service.PostService;

import com.example.StreamCraft.dto.Commuity.CommentResponseDto;
import com.example.StreamCraft.Manager.CommentManager;
import com.example.StreamCraft.Repository.post.CommentRepository;
import com.example.StreamCraft.Entity.communitypost.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityCommentService {

    private final CommentManager commentManager;
    private final CommentRepository commentRepository;

    /**
     * 댓글 작성
     * @param dto 댓글 정보 DTO
     * @param postId 게시글 ID
     * @param bearerToken "Bearer ~" 형식 토큰
     * @return 성공 여부
     */
    public boolean writeComment(CommentResponseDto dto, int postId, String bearerToken){
        String jwt = bearerToken.substring(7);
        return commentManager.createComment(dto, postId, jwt);
    }

    /**
     * 게시글에 달린 댓글 조회
     * @param postId 게시글 ID
     * @return 댓글 목록
     */
    public List<CommentEntity> getCommentsByPostId(int postId) {
        return commentRepository.findByPostEntityPostId(postId).orElse(List.of());
    }

    /**
     * 댓글 삭제
     */
    public ResponseEntity<String> removeComment(int commentId){
        commentRepository.deleteByCommentId(commentId);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }
}
