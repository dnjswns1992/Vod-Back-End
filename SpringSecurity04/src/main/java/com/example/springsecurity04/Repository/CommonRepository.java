package com.example.springsecurity04.Repository;

import com.example.springsecurity04.Table.Common.CommonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommonRepository extends JpaRepository<CommonEntity,Integer> {


    Optional<CommonEntity> findByUsername(String username);
}
