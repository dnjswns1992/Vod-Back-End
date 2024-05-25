package com.example.springsecurity04.Dto.Commuity;

import com.example.springsecurity04.Table.Post.CommentEntity;

import java.util.List;
import java.util.Optional;


public record CommunityDto(PostDto dto, Optional<List<CommentEntity>> commentEntities) {

}

