package com.example.springsecurity04.Repository.VideoRepository;

import com.example.springsecurity04.Table.Video.EpisodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface EpisodeRepository extends JpaRepository<EpisodeEntity,Integer> {

   List<EpisodeEntity> findByUploadMainTitleEntityUploadMainTitleEntityId(int id);

}
