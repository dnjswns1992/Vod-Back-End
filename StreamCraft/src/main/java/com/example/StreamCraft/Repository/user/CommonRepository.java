package com.example.StreamCraft.Repository.user;

import com.example.StreamCraft.Entity.commom.MergedUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


//공통 사용자 리파지토리
// Oauth와 일반 폼 로그인 사용자를 하나로 묶어 통합 관리하는 리파지토리
//실제 웹 사이트에선 폼 로그인 사용자로 묶은 테이블로 jwt로 관리하며 유저를 관리한다.
@Repository
public interface CommonRepository extends JpaRepository<MergedUserEntity,Integer> {


    Optional<MergedUserEntity> findByUsername(String username);
}
