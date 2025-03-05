package com.nikhil.tokenApp.service;

import com.nikhil.tokenApp.domain.DeviceStationResponse;
import com.nikhil.tokenApp.domain.MasterStationResponse;
import com.nikhil.tokenApp.entity.StationEntity;
import com.nikhil.tokenApp.mapper.DeviceStationMapper;
import com.nikhil.tokenApp.mapper.MasterStationMapper;
import com.nikhil.tokenApp.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationService {

    @Autowired
    StationRepository stationRepository;

    @Autowired
    DeviceStationMapper deviceStationMapper;

    @Autowired
    MasterStationMapper masterStationMapper;

    public List<DeviceStationResponse> getDeviceStations() {
        List<StationEntity> allStations = stationRepository.findAll();
        List<DeviceStationResponse> lstStation = allStations.stream().map( station ->
                deviceStationMapper.convertToDeviceStationResponse(station)).collect(Collectors.toList());
        return lstStation;
    }

    public List<MasterStationResponse> getToStations(Integer stationId, Integer routeNo) {
        List<StationEntity> allStations = stationRepository.findByStationIdNotAndRouteNo(stationId, routeNo);
        List<MasterStationResponse> listStations = masterStationMapper.convertToMastertStationResponseList(allStations);
        return listStations;
    }
}
