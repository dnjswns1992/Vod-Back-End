package com.example.StreamCraft.Service.PostService;


import com.example.StreamCraft.dto.Commuity.PostWithDto;
import com.example.StreamCraft.dto.Commuity.PostResponseDto;
import com.example.StreamCraft.Manager.PostManager;
import com.example.StreamCraft.Repository.CommentRepository;
import com.example.StreamCraft.Repository.PostRepository;
import com.example.StreamCraft.Table.Post.CommentEntity;
import com.example.StreamCraft.Table.Post.PostEntity;
import com.example.StreamCraft.UserAccount.TransferModelMapper;
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

    public boolean createPost(PostResponseDto dto){
      return   postManager.createPost(dto);
    }
    public List<PostEntity> bringPost(){
        List<PostEntity> postAll = postRepository.findAll();

        for(PostEntity post : postAll) {
            int commentCount = commentRepository.countByPostEntity(post);
            post.setCommentCount(commentCount);
        }

        return postAll;
    }

    public PostWithDto DetailsPostView(Integer postId) {
        Optional<PostEntity> byPostId = repository.findByPostId(postId);
        PostResponseDto mapperTransfer = (PostResponseDto) mapper.getTransfer(byPostId.get(), PostResponseDto.class);

        if(mapperTransfer != null) {
            postRepository.incrementHit(postId);
            Optional<List<CommentEntity>> commentEntityList = commentRepository.findByPostEntityOrderByCreateAtDesc(byPostId.get());

            if(commentEntityList.isPresent()) {
               return new PostWithDto(mapperTransfer,commentEntityList);
            }

        }

        return null;
    }


    public boolean removePost(int postId) {
       return postManager.removePost(postId);
    }
}
