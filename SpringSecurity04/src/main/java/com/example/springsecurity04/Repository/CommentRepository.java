package com.example.springsecurity04.Repository;

import com.example.springsecurity04.Table.Post.CommentEntity;
import com.example.springsecurity04.Table.Post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity,Integer> {

    Optional<List<CommentEntity>> findByPostEntityOrderByCreateAtDesc(PostEntity postEntity);
}
