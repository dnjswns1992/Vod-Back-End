package com.example.StreamCraft.Repository.episode.movie;

import com.example.StreamCraft.Entity.episode.movie.MovieEpisodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 영화 에피소드 전용 레포지토리
 * 영화 S3에 등록되었던 aws-url을 프론트에서 뿌려진 id를 받아서 DB에서 ID를 찾음
 * 그 아이디가 해당되면 ID와 의존관계에 있던 애니메이션 에피소드들을 가져와서 목록에서 보여준다.
 *  예시) 덕혜옹주 클릭 -> 덕혜옹주 1기 2기 3기 목록에 보여줌
 */
@Transactional
public interface MovieEpisodeRepository extends JpaRepository<MovieEpisodeEntity, Long> {

    @Query("SELECT m FROM MovieEpisodeEntity m WHERE m.uploadMainTitleEntity.uploadMainTitleEntityId = :id")
    List<MovieEpisodeEntity> findByMainTitleId(@Param("id") int id);
}