package com.example.StreamCraft.Repository;

import com.example.StreamCraft.Table.UserAccount.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Transactional
@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {


    public Optional<UserEntity> findUserEntityByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    boolean existsByNickName(String nickName);
}
