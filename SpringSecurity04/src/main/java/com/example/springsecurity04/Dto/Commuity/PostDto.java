package com.example.springsecurity04.Dto.Commuity;


import lombok.Data;

import java.time.LocalDate;

@Data
public class PostDto {

    private int postId;
    private String content;
    private String title;
    private String username;
    private int postHit;
    private LocalDate createTime;
    private String nickname;
}
