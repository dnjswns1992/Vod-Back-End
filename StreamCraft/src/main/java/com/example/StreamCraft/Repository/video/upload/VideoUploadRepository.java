package com.example.StreamCraft.Repository.video.upload;

import com.example.StreamCraft.Entity.Video.UploadMainTitleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
/**
 * ✅ 업로드된 영상의 메타데이터(제목, 장르 등)를 관리하는 리포지토리
 * - AWS S3에 업로드된 영상을 DB에서 조회/검색
 * - 또는 업로드된 영상을 S3에 저장 -> aws s3 url을 받아 DB에 저장합니다.
 */
@Transactional
public interface VideoUploadRepository extends JpaRepository<UploadMainTitleEntity,Integer> {

    //타이틀 제목이 포함된걸로 찾음 예) 프리렌 -> 장송의 프리렌
Optional<UploadMainTitleEntity> findByTitleContaining(String title);
Optional<UploadMainTitleEntity> findByTitle(String title);
  //타이틀 제목과 장르로 찾음 , 프리렌 , 로맨스 -> 장송의 프리렌
Optional<UploadMainTitleEntity> findByTitleContainingAndGenre(String title,String genre);

// 장르로 찾음
List<UploadMainTitleEntity> findByGenre(String Genre);



}
