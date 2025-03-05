package com.nikhil.tokenApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nikhil.tokenApp.entity.TokenEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {

    // Get all tokens (already available via findAll())

    // Custom Query to find all tokens with a specific status
//    List<TokenEntity> findByTokenStatus(int status);
}
