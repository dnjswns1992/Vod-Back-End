package com.example.springsecurity04.Repository.VideoRepository;

import com.example.springsecurity04.Table.Video.UploadMainTitleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface UploadMainTitleRepository extends JpaRepository<UploadMainTitleEntity,Integer> {

Optional<UploadMainTitleEntity> findByTitleContaining(String title);
Optional<UploadMainTitleEntity> findByTitle(String title);
Optional<UploadMainTitleEntity> findByTitleContainingAndGenre(String title,String genre);

Optional<UploadMainTitleEntity> findByGenre(String Genre);



}
