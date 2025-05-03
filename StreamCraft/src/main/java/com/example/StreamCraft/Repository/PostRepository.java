package com.example.StreamCraft.Repository;

import com.example.StreamCraft.Table.Post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface PostRepository extends JpaRepository<PostEntity,Integer> {

    Optional<PostEntity> findByPostId(Integer postId);
    Optional<PostEntity> findByUsername(String username);
    void deleteByPostId(int postId);
    @Query("update PostEntity p set p.postHit = p.postHit +1 where p.postId = :postId")
    @Modifying
    void incrementHit(@Param("postId") int postId);
}
