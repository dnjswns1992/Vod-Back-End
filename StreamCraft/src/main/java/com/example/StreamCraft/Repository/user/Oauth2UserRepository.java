package com.example.StreamCraft.Repository.user;

import com.example.StreamCraft.Entity.user.Oauth2Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//Oauth2 유저를 저장하는 리파지토리
public interface Oauth2UserRepository extends JpaRepository<Oauth2Entity,Integer> {

    Optional<Oauth2Entity> findByEmail(String email);
}
