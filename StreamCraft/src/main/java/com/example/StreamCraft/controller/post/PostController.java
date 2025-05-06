package com.example.StreamCraft.controller.post;
import com.example.StreamCraft.dto.Commuity.PostWithDto;
import com.example.StreamCraft.dto.Commuity.PostResponseDto;
import com.example.StreamCraft.service.PostService.CommunityPostService;
import com.example.StreamCraft.Entity.communitypost.PostEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor

/**
 * 커뮤니티 게시글 CRUD를 담당하는 컨트롤러
 */

public class PostController {

    private final CommunityPostService service;

    /**
     * 게시글 작성
     */

    @PostMapping("/user/createPost")
    @Tag(name = "게시글 등록 컨트롤러, description : 게시글을 등록 함")
    public ResponseEntity<?> createPost(@RequestBody PostResponseDto dto){

        boolean post = service.createPost(dto);
        if(post) return ResponseEntity.status(HttpStatus.OK).body("글이 정상적으로 등록 되었습니다");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("글이 등록되지 않았습니다.");
    }

    /**
     * 게시글 상세 조회
     */

    @GetMapping("/user/postDetail/{postId}")
    @Tag(name = "게시글 상세 조회, description : 사용자가 포스트를 눌렀을 때 상세정보를 가져옴")
    public ResponseEntity<?> DetailsViewPost(@PathVariable Integer postId){

        PostWithDto dto = service.getPostDetails(postId);

        return dto == null ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    /**
     * 전체 게시글 목록 조회
     */
    @GetMapping("/user/bring/post")
    @Tag(name = "전체 조회 컨트롤러, description : 사용자가 커뮤니티 사이트에 왔을 때 모든 게시글을 가져옴")
    public ResponseEntity bringPost() {
        List<PostEntity> postEntities = service.getAllPosts();

        return ResponseEntity.ok(postEntities);
    }

    /**
     * 게시글 삭제
     */

    @GetMapping("user/postRemove/{postId}")
    @Tag(name = "게시글 삭제 컨트롤러, description : 게시글 삭제 함")
    public ResponseEntity<?> postRemove(@PathVariable int postId) {

        boolean check = service.deletePost(postId);

        return check == true ? ResponseEntity.status(HttpStatus.OK).body("게시글이 성공적으로 삭제 되었습니다."):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("사용자가 다릅니다.");
    }
}
