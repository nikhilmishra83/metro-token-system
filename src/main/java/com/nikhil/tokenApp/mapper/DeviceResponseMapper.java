package com.nikhil.tokenApp.mapper;

import com.nikhil.tokenApp.domain.DeviceResponse;
import com.nikhil.tokenApp.entity.DeviceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceResponseMapper {

    DeviceResponse converToDeviceResponse(DeviceEntity deviceEntity);
}
