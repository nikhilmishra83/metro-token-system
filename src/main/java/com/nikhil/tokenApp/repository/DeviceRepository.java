package com.nikhil.tokenApp.repository;

import com.nikhil.tokenApp.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface DeviceRepository extends JpaRepository<DeviceEntity, Integer> {
    List<DeviceEntity> findByStationIdAndDeviceInUse(Integer stationId, Boolean deviceInUse);

}
