package com.example.springsecurity04.Dto.Commuity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
public class CommentDto {

    private int commentId;
    private String nickname;
    private String title;
    private String content;
    private LocalDate createAt;
    private int postId;


}
