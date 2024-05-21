package com.example.springsecurity04.Controller.PostController;


import com.example.springsecurity04.Dto.Commuity.CommentDto;
import com.example.springsecurity04.Service.PostService.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/user/comment/write/{postId}")
    public ResponseEntity<?> commentWrite(@RequestBody CommentDto commentDto, @PathVariable int postId){

        boolean check = commentService.CommentWriteService(commentDto, postId);

        return check == true ? ResponseEntity.status(HttpStatus.OK).body("댓글이 등록 되었습니다.")
        : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시글 등록에 실패 했습니다.");
    }
}
