package com.example.StreamCraft.Manager;


import com.example.StreamCraft.dto.Commuity.PostResponseDto;
import com.example.StreamCraft.dto.user.UserInfoResponseDto;
import com.example.StreamCraft.jwtutil.AuthenticatedUserUtil;
import com.example.StreamCraft.Repository.user.CommonRepository;
import com.example.StreamCraft.Repository.post.PostRepository;
import com.example.StreamCraft.Entity.commom.MergedUserEntity;
import com.example.StreamCraft.Entity.communitypost.PostEntity;
import com.example.StreamCraft.UserAccount.TransferModelMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * 게시글 생성 및 삭제를 담당하는 매니저 클래스
 */
@RequiredArgsConstructor
public class PostManager {

    private final CommonRepository commonRepository;
    private final PostRepository postRepository;

    /**
     * 게시글 생성
     * @param postResponseDto 게시글 정보 DTO
     * @return 성공 여부
     */
    public boolean createPost(PostResponseDto postResponseDto){
        TransferModelMapper<PostResponseDto,PostEntity> mapper = new TransferModelMapper<>();
        PostEntity transfer = mapper.getTransfer(postResponseDto, PostEntity.class);

        MergedUserEntity mergedUserEntity = commonRepository.findByUsername(postResponseDto.getUsername()).get();

        transfer.setUsername(mergedUserEntity.getUsername());
        transfer.setMergedUserEntity(mergedUserEntity);
        transfer.setNickname(mergedUserEntity.getNickname());

        postRepository.save(transfer);


        return true;
    }
    /**
     * 게시글 삭제
     * @param postId 삭제할 게시글 ID
     * @return 삭제 성공 여부 (작성자 일치 여부 포함)
     */
    public boolean removePost(int postId){
        UserInfoResponseDto userAccount = AuthenticatedUserUtil.getUserAccount();

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
