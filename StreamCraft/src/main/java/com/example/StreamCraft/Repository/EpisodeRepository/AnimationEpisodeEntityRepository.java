package com.example.StreamCraft.Repository.EpisodeRepository;

import com.example.StreamCraft.Table.EpsidoeEntity.AnimationEpisodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimationEpisodeEntityRepository extends JpaRepository<AnimationEpisodeEntity,Integer> {

  List<AnimationEpisodeEntity> findByUploadMainTitleEntityUploadMainTitleEntityId(int id);
  List<AnimationEpisodeEntity> findByUploadMainTitleEntityUploadMainTitleEntityIdAndVideoType(int id, String videoType);
}
