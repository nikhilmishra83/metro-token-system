package com.nikhil.tokenApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nikhil.tokenApp.entity.StationEntity;

import java.util.List;

public interface StationRepository extends JpaRepository<StationEntity, Integer> {

    List<StationEntity> findByStationIdNotAndRouteNo(Integer stationId, Integer routeNo);
}