package com.example.springsecurity04.Repository.VideoRepository;

import com.example.springsecurity04.Table.Video.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<VideoEntity,Integer> {
}
