package com.example.springsecurity04.Controller.PostController;


import com.example.springsecurity04.Dto.Commuity.CommentDto;
import com.example.springsecurity04.Service.PostService.CommentService;
import com.example.springsecurity04.Table.Post.CommentEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/user/comment/write/{postId}")
    public ResponseEntity<?> commentWrite(@RequestBody CommentDto commentDto,
                                          @PathVariable int postId,@RequestHeader("Authorization")String token){

        boolean check = commentService.CommentWriteService(commentDto, postId,token);
        log.info("check = {}",check);
        return check == true ? ResponseEntity.status(HttpStatus.OK).body("댓글이 등록 되었습니다.")
        : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시글 등록에 실패 했습니다.");
    }
    @GetMapping("/user/comment/bring/{postId}")
    public ResponseEntity BringComment(@PathVariable int postId){
        List<CommentEntity> commentEntities = commentService.CommentBring(postId);
        return ResponseEntity.ok(commentEntities);
    }
    @GetMapping("/user/comment/remove/{commentId}")
    public ResponseEntity removeComment(@PathVariable int commentId){
        ResponseEntity responseEntity = commentService.removeComment(commentId);

        return responseEntity;
    }
}
