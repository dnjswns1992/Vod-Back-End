package com.example.springsecurity04.Controller.PostController;
import com.example.springsecurity04.Dto.Commuity.CommunityDto;
import com.example.springsecurity04.Dto.Commuity.PostDto;
import com.example.springsecurity04.Service.PostService.PostService;
import com.example.springsecurity04.Table.Post.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    @PostMapping("/user/createPost")
    public ResponseEntity<?> createPost(@RequestBody PostDto dto){

        boolean post = service.createPost(dto);
        if(post) return ResponseEntity.status(HttpStatus.OK).body("글이 정상적으로 등록 되었습니다");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("글이 등록되지 않았습니다.");
    }

    @GetMapping("/user/postDetail/{postId}")
    public ResponseEntity<?> DetailsViewPost(@PathVariable Integer postId){

        CommunityDto dto = service.DetailsPostView(postId);

        return dto == null ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.status(HttpStatus.OK).body(dto);
    }
    @GetMapping("/user/bring/post")
    public ResponseEntity bringPost() {
        List<PostEntity> postEntities = service.bringPost();

        return ResponseEntity.ok(postEntities);
    }
    @GetMapping("user/postRemove/{postId}")
    public ResponseEntity<?> postRemove(@PathVariable int postId) {

        boolean check = service.removePost(postId);



        return check == true ? ResponseEntity.status(HttpStatus.OK).body("게시글이 성공적으로 삭제 되었습니다."):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("사용자가 다릅니다.");
    }
}
