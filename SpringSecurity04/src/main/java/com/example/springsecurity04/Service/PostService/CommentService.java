package com.example.springsecurity04.Service.PostService;


import com.example.springsecurity04.Dto.Commuity.CommentDto;
import com.example.springsecurity04.Manager.CommentManager;
import com.example.springsecurity04.Oauth2.ProviderUser.ProviderUser;
import com.example.springsecurity04.Repository.CommentRepository;
import com.example.springsecurity04.Table.Post.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentManager commentManager;
    private final CommentRepository commentRepository;

    public boolean CommentWriteService(CommentDto commentDto,int postId,String token){

        String JwtToken = token.substring(7);
        return commentManager.createComment(commentDto,postId,JwtToken);
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
