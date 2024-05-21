package com.example.springsecurity04.Repository;

import com.example.springsecurity04.Table.Oauth2Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Oauth2UserRepository extends JpaRepository<Oauth2Entity,Integer> {

    Optional<Oauth2Entity> findByEmail(String email);
}
