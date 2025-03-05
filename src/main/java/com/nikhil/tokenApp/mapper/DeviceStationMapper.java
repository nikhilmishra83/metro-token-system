package com.nikhil.tokenApp.mapper;

import com.nikhil.tokenApp.domain.DeviceStationResponse;
import com.nikhil.tokenApp.entity.StationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceStationMapper {

    DeviceStationResponse convertToDeviceStationResponse(StationEntity stationEntity);
}
