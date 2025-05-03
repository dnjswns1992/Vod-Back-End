package com.example.StreamCraft.service.PostService;


import com.example.StreamCraft.dto.Commuity.PostWithDto;
import com.example.StreamCraft.dto.Commuity.PostResponseDto;
import com.example.StreamCraft.Manager.PostManager;
import com.example.StreamCraft.Repository.post.CommentRepository;
import com.example.StreamCraft.Repository.post.PostRepository;
import com.example.StreamCraft.Entity.communitypost.CommentEntity;
import com.example.StreamCraft.Entity.communitypost.PostEntity;
import com.example.StreamCraft.UserAccount.TransferModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ✅ 게시글(Post) 관련 비즈니스 로직 처리 서비스
 * - 게시글 생성, 조회, 삭제, 상세 보기 기능을 담당
 */
@Service
@RequiredArgsConstructor
public class CommunityPostService {

    private final TransferModelMapper mapper;
    private final CommentRepository commentRepository;

    private final PostRepository postRepository;
    private final PostRepository repository;
    private final PostManager postManager;

    /**
     * ✅ 게시글 생성
     * @param dto 게시글 정보 DTO
     * @return 성공 여부
     */
    public boolean createPost(PostResponseDto dto){
      return   postManager.createPost(dto);
    }
    /**
     * ✅ 전체 게시글 목록 조회 + 댓글 개수 포함
     * @return 게시글 목록
     */
    public List<PostEntity> getAllPosts(){
        List<PostEntity> postAll = postRepository.findAll();

        for(PostEntity post : postAll) {
            int commentCount = commentRepository.countByPostEntity(post);
            post.setCommentCount(commentCount);// 댓글 수 세팅
        }

        return postAll;
    }

    /**
     * ✅ 게시글 상세 보기
     * - 게시글 정보 + 댓글 목록 포함 반환
     *
     * @param postId 게시글 ID
     * @return 게시글 DTO + 댓글 리스트 DTO
     */
    public PostWithDto getPostDetails(Integer postId) {
        Optional<PostEntity> byPostId = repository.findByPostId(postId);
        PostResponseDto mapperTransfer = (PostResponseDto) mapper.getTransfer(byPostId.get(), PostResponseDto.class);

        if(mapperTransfer != null) {
            // 조회수 증가
            postRepository.incrementHit(postId);
            // 댓글 정렬된 목록 조회
            Optional<List<CommentEntity>> commentEntityList = commentRepository.findByPostEntityOrderByCreateAtDesc(byPostId.get());

            if(commentEntityList.isPresent()) {
               return new PostWithDto(mapperTransfer,commentEntityList);
            }

        }

        return null;
    }


    public boolean deletePost(int postId) {
       return postManager.removePost(postId);
    }
}
