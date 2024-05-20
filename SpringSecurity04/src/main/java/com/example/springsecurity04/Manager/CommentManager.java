package com.example.springsecurity04.Manager;


import com.example.springsecurity04.Dto.Commuity.CommentDto;
import com.example.springsecurity04.Dto.UserInformation;
import com.example.springsecurity04.JwtUtil.DetailsAccountUserDetail;
import com.example.springsecurity04.Repository.CommentRepository;
import com.example.springsecurity04.Repository.PostRepository;
import com.example.springsecurity04.Table.Post.CommentEntity;
import com.example.springsecurity04.Table.Post.PostEntity;
import com.example.springsecurity04.UserAccount.TransferModelMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Data
public class CommentManager {


    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public boolean createComment(CommentDto commentDto,int postId){

        UserInformation userAccount = DetailsAccountUserDetail.getUserAccount();

        Optional<PostEntity> byPostId = postRepository.findByPostId(postId);

        if(byPostId.isPresent()) {
            TransferModelMapper<CommentDto, CommentEntity> transferModelMapper = new TransferModelMapper<>();

            CommentEntity transfer = transferModelMapper.getTransfer(commentDto, CommentEntity.class);
            transfer.setPostEntity(byPostId.get());
            transfer.setNickname(userAccount.getNickname());
            transfer.setUsername(userAccount.getUsername());

                commentRepository.save(transfer);

            return true;
        }
        return false;
    }
}
