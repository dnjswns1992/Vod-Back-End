package com.example.StreamCraft.Repository.post;

import com.example.StreamCraft.Entity.communitypost.CommentEntity;
import com.example.StreamCraft.Entity.communitypost.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<CommentEntity,Integer> {

    Optional<List<CommentEntity>> findByPostEntityOrderByCreateAtDesc(PostEntity postEntity);
    int countByPostEntity(PostEntity postEntity);
    Optional<List<CommentEntity>> findByPostEntityPostId(int postId);

    void deleteByCommentId(int commentId);
}
