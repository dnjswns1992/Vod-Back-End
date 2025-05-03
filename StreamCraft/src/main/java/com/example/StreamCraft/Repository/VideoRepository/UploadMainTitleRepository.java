package com.example.StreamCraft.Repository.VideoRepository;

import com.example.StreamCraft.Table.Video.UploadMainTitleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface UploadMainTitleRepository extends JpaRepository<UploadMainTitleEntity,Integer> {

Optional<UploadMainTitleEntity> findByTitleContaining(String title);
Optional<UploadMainTitleEntity> findByTitle(String title);
Optional<UploadMainTitleEntity> findByTitleContainingAndGenre(String title,String genre);

List<UploadMainTitleEntity> findByGenre(String Genre);



}
