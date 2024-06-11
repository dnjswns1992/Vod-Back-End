package com.example.springsecurity04.Repository.EpisodeRepository;

import com.example.springsecurity04.Table.EpsidoeEntity.AnimationEpisodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.List;

public interface AnimationEpisodeEntityRepository extends JpaRepository<AnimationEpisodeEntity,Integer> {

  List<AnimationEpisodeEntity> findByUploadMainTitleEntityUploadMainTitleEntityId(int id);
  List<AnimationEpisodeEntity> findByUploadMainTitleEntityUploadMainTitleEntityIdAndVideoType(int id, String videoType);
}
