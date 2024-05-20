package com.example.springsecurity04.Service.PostService;


import com.example.springsecurity04.Dto.Commuity.CommunityDto;
import com.example.springsecurity04.Dto.Commuity.PostDto;
import com.example.springsecurity04.Manager.PostManager;
import com.example.springsecurity04.Repository.CommentRepository;
import com.example.springsecurity04.Repository.PostRepository;
import com.example.springsecurity04.Table.Post.CommentEntity;
import com.example.springsecurity04.Table.Post.PostEntity;
import com.example.springsecurity04.UserAccount.TransferModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final TransferModelMapper mapper;
    private final CommentRepository commentRepository;

    private final PostRepository postRepository;
    private final PostRepository repository;
    private final PostManager postManager;

    public boolean createPost(PostDto dto){
      return   postManager.createPost(dto);
    }

    public CommunityDto DetailsPostView(Integer postId) {
        Optional<PostEntity> byPostId = repository.findByPostId(postId);
        PostDto mapperTransfer = (PostDto) mapper.getTransfer(byPostId.get(), PostDto.class);

        if(mapperTransfer != null) {
            postRepository.incrementHit(postId);
            Optional<List<CommentEntity>> commentEntityList = commentRepository.findByPostEntityOrderByCreateAtDesc(byPostId.get());

            if(commentEntityList.isPresent()) {
               return new CommunityDto(mapperTransfer,commentEntityList);
            }

        }

        return null;
    }

    public boolean removePost(int postId) {
       return postManager.removePost(postId);
    }
}
