package com.example.springsecurity04.Repository.EpisodeRepository;

import com.example.springsecurity04.Table.EpsidoeEntity.DramaEpisodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface DramaEpisodeRepository extends JpaRepository<DramaEpisodeEntity,Integer> {
}
