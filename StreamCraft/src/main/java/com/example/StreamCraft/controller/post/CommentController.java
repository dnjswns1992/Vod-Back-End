package com.example.StreamCraft.controller.post;


import com.example.StreamCraft.dto.Commuity.CommentResponseDto;
import com.example.StreamCraft.service.PostService.CommunityCommentService;
import com.example.StreamCraft.Entity.communitypost.CommentEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j

/**
 * 댓글 작성
 *
 * @param commentDto 댓글 내용
 * @param postId     댓글이 달릴 게시글 ID
 * @param token      JWT 토큰 (Authorization 헤더)
 */
public class CommentController {

    private final CommunityCommentService communityCommentService;


    @PostMapping("/user/comment/write/{postId}")
    @Tag(name = "댓글등록 컨트롤러, description : 사용자가 댓글을 등록할 때 사용")
    public ResponseEntity<?> commentWrite(@RequestBody CommentResponseDto commentResponseDto,
                                          @PathVariable int postId,@RequestHeader("Authorization")String token){

        boolean check = communityCommentService.writeComment(commentResponseDto, postId,token);
        log.info("check = {}",check);
        return check == true ? ResponseEntity.status(HttpStatus.OK).body("댓글이 등록 되었습니다.")
        : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시글 등록에 실패 했습니다.");
    }

    /**
     * 게시글에 달린 댓글 목록 조회
     */

    @GetMapping("/user/comment/bring/{postId}")
    @Tag(name = "댓글 반환 컨트롤러, description : 게시글에 달린 댓글을 반환 함")
    public ResponseEntity BringComment(@PathVariable int postId){
        List<CommentEntity> commentEntities = communityCommentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(commentEntities);
    }

    /**
     * 댓글 삭제
     */

    @GetMapping("/user/comment/remove/{commentId}")
    @Tag(name = "댓글 삭제 컨트롤러, description : 게시글에 달린 댓글을 삭제함")
    public ResponseEntity removeComment(@PathVariable int commentId){
        ResponseEntity responseEntity = communityCommentService.removeComment(commentId);

        return responseEntity;
    }
}
