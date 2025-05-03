package com.example.StreamCraft.Service.PostService;


import com.example.StreamCraft.dto.Commuity.CommentResponseDto;
import com.example.StreamCraft.Manager.CommentManager;
import com.example.StreamCraft.Repository.CommentRepository;
import com.example.StreamCraft.Table.Post.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentManager commentManager;
    private final CommentRepository commentRepository;

    public boolean CommentWriteService(CommentResponseDto commentResponseDto, int postId, String token){

        String JwtToken = token.substring(7);
        return commentManager.createComment(commentResponseDto,postId,JwtToken);
    }
    public List<CommentEntity> CommentBring(int postId) {
        List<CommentEntity> commentEntities = commentRepository.findByPostEntityPostId(postId).get();

        return commentEntities;
    }
    public ResponseEntity removeComment(int commentId){
        commentRepository.deleteByCommentId(commentId);

        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }
}
