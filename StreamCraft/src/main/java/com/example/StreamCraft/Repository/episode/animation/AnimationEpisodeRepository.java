package com.example.StreamCraft.Repository.episode.animation;

import com.example.StreamCraft.Entity.episode.animation.AnimationEpisodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * 애니메이션 에피소드 전용 레포지토리
 * 애니메이션이 S3에 등록되었던 aws-url을 프론트에서 뿌려진 id를 받아서 DB에서 ID를 찾음
 * 그 아이디가 해당되면 ID와 의존관계에 있던 애니메이션 에피소드들을 가져와서 목록에서 보여준다.
 *  예시) 장송의 프리렌 클릭 -> 프리렌 1기에 해당하는 에피소드 (1 ~ 24화 까지) 목록에 보여줌
 */
public interface AnimationEpisodeRepository extends JpaRepository<AnimationEpisodeEntity,Integer> {

  List<AnimationEpisodeEntity> findByUploadMainTitleEntityUploadMainTitleEntityId(int id);
  List<AnimationEpisodeEntity> findByUploadMainTitleEntityUploadMainTitleEntityIdAndVideoType(int id, String videoType);
}
