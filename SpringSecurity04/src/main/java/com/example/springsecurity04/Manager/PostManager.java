package com.example.springsecurity04.Manager;


import com.example.springsecurity04.Dto.Commuity.PostDto;
import com.example.springsecurity04.Dto.UserInformation;
import com.example.springsecurity04.JwtUtil.DetailsAccountUserDetail;
import com.example.springsecurity04.JwtUtil.JsonWebToken;
import com.example.springsecurity04.Repository.CommonRepository;
import com.example.springsecurity04.Repository.PostRepository;
import com.example.springsecurity04.Table.Common.CommonEntity;
import com.example.springsecurity04.Table.Post.PostEntity;
import com.example.springsecurity04.UserAccount.TransferModelMapper;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class PostManager {

    private final CommonRepository commonRepository;
    private final PostRepository postRepository;

    public boolean createPost(PostDto postDto){
        TransferModelMapper<PostDto,PostEntity> mapper = new TransferModelMapper<>();
        PostEntity transfer = mapper.getTransfer(postDto, PostEntity.class);

        CommonEntity commonEntity = commonRepository.findByUsername(postDto.getUsername()).get();

        transfer.setUsername(commonEntity.getUsername());
        transfer.setCommonEntity(commonEntity);

        postRepository.save(transfer);


        return true;
    }
    public boolean removePost(int postId){
        UserInformation userAccount = DetailsAccountUserDetail.getUserAccount();

        Optional<PostEntity> byPostId = postRepository.findByPostId(postId);

        if(byPostId.isPresent()) {

            if(byPostId.get().getUsername().equals(userAccount.getUsername())) {
                postRepository.deleteByPostId(postId);
                return true;
            }
        }
        return false;
    }

}
