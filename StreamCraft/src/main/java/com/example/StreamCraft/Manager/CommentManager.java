package com.example.StreamCraft.Manager;


import com.example.StreamCraft.dto.Commuity.CommentResponseDto;
import com.example.StreamCraft.jwtutil.JwtTokenProvider;
import com.example.StreamCraft.Repository.CommentRepository;
import com.example.StreamCraft.Repository.CommonRepository;
import com.example.StreamCraft.Repository.PostRepository;
import com.example.StreamCraft.Table.Common.CommonEntity;
import com.example.StreamCraft.Table.Post.CommentEntity;
import com.example.StreamCraft.Table.Post.PostEntity;
import com.example.StreamCraft.UserAccount.TransferModelMapper;
import io.jsonwebtoken.Claims;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


/**
 * 댓글 관련 비즈니스 로직을 처리하는 Manager 클래스
 * - 댓글 작성 시 유저 정보 확인, 게시글 연관, 댓글 저장 등을 수행
 */
@RequiredArgsConstructor
@Data
public class CommentManager {


    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommonRepository commonRepository;


    /**
     * 댓글 생성 로직
     *
     * @param commentResponseDto 댓글 내용 DTO
     * @param postId             대상 게시글 ID
     * @param token              JWT 토큰 (작성자 인증용)
     * @return 저장 성공 여부
     */
    public boolean createComment(CommentResponseDto commentResponseDto, int postId, String token){

        // JWT 토큰에서 사용자 정보 추출
        Claims userToken = JwtTokenProvider.getToken(token);
        String username = userToken.get("username", String.class);

        // 게시글 존재 여부 확인

        Optional<PostEntity> byPostId = postRepository.findByPostId(postId);

        // 공통 사용자 정보 조회

        CommonEntity commonEntity = commonRepository.findByUsername(username).get();

        // 게시글이 존재하는 경우 댓글 작성 처리

        if(byPostId.isPresent()) {
            // DTO → Entity 변환
            TransferModelMapper<CommentResponseDto, CommentEntity> transferModelMapper = new TransferModelMapper<>();


            // 댓글에 사용자 및 게시글 정보 세팅

            CommentEntity transfer = transferModelMapper.getTransfer(commentResponseDto, CommentEntity.class);
            transfer.setPostEntity(byPostId.get());
            transfer.setNickname(commonEntity.getNickname());
            transfer.setUsername(commonEntity.getUsername());
            transfer.setTitle(byPostId.get().getTitle());
            transfer.setContent(commentResponseDto.getContent());
            transfer.setPostEntity(byPostId.get());

            // DB에 댓글 저장

            commentRepository.save(transfer);

            return true;
        }
        // 게시글이 없으면 실패

        return false;
    }
}
