package com.example.springsecurity04.Manager;


import com.example.springsecurity04.Dto.Commuity.CommentDto;
import com.example.springsecurity04.Dto.UserInformation;
import com.example.springsecurity04.JwtUtil.DetailsAccountUserDetail;
import com.example.springsecurity04.JwtUtil.JsonWebToken;
import com.example.springsecurity04.Repository.CommentRepository;
import com.example.springsecurity04.Repository.CommonRepository;
import com.example.springsecurity04.Repository.PostRepository;
import com.example.springsecurity04.Table.Common.CommonEntity;
import com.example.springsecurity04.Table.Post.CommentEntity;
import com.example.springsecurity04.Table.Post.PostEntity;
import com.example.springsecurity04.UserAccount.TransferModelMapper;
import io.jsonwebtoken.Claims;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Data
public class CommentManager {


    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommonRepository commonRepository;

    public boolean createComment(CommentDto commentDto,int postId,String token){

        Claims userToken = JsonWebToken.getToken(token);
        String username = userToken.get("username", String.class);

        Optional<PostEntity> byPostId = postRepository.findByPostId(postId);
        CommonEntity commonEntity = commonRepository.findByUsername(username).get();

        if(byPostId.isPresent()) {
            TransferModelMapper<CommentDto, CommentEntity> transferModelMapper = new TransferModelMapper<>();

            CommentEntity transfer = transferModelMapper.getTransfer(commentDto, CommentEntity.class);
            transfer.setPostEntity(byPostId.get());
            transfer.setNickname(commonEntity.getNickname());
            transfer.setUsername(commonEntity.getUsername());
            transfer.setTitle(byPostId.get().getTitle());
            transfer.setContent(commentDto.getContent());
            transfer.setPostEntity(byPostId.get());

                commentRepository.save(transfer);

            return true;
        }
        return false;
    }
}
