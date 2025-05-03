package com.example.StreamCraft.Repository.user;

import com.example.StreamCraft.Entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//일반 폼 로그인 사용자를 관리하는 리파지토리
@Transactional
@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {


    public Optional<UserEntity> findUserEntityByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    boolean existsByNickName(String nickName);
}
