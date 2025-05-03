package com.example.StreamCraft.Repository.EpisodeRepository;

import com.example.StreamCraft.Table.EpsidoeEntity.MovieEpisodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface MovieEpisodeRepository extends JpaRepository<MovieEpisodeEntity, Long> {

    @Query("SELECT m FROM MovieEpisodeEntity m WHERE m.uploadMainTitleEntity.uploadMainTitleEntityId = :id")
    List<MovieEpisodeEntity> findByMainTitleId(@Param("id") int id);
}