package com.example.springsecurity04.Service.PostService;


import com.example.springsecurity04.Dto.Commuity.CommentDto;
import com.example.springsecurity04.Manager.CommentManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentManager commentManager;


    public boolean CommentWriteService(CommentDto commentDto,int postId){

        return commentManager.createComment(commentDto,postId);
    }
}
