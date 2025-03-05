package com.nikhil.tokenApp.repository;

import com.nikhil.tokenApp.entity.RatesEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatesRepository extends JpaRepository<RatesEntity, Integer> {
}
