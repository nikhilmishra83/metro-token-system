package com.nikhil.tokenApp.service;

import com.nikhil.tokenApp.domain.DeviceResponse;
import com.nikhil.tokenApp.domain.DeviceTypeResponse;
import com.nikhil.tokenApp.entity.DeviceEntity;
import com.nikhil.tokenApp.enums.DeviceTypeEnum;
import com.nikhil.tokenApp.mapper.DeviceResponseMapper;
import com.nikhil.tokenApp.mapper.DeviceStationMapper;
import com.nikhil.tokenApp.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    DeviceResponseMapper deviceResponseMapper;

    public List<DeviceTypeResponse> getDeviceTypes() {
        return Arrays.stream(DeviceTypeEnum.values())
                .map(device -> new DeviceTypeResponse(device.getDeviceTypeId(), device.getDeviceType()))
                .collect(Collectors.toList());
    }

    public List<DeviceResponse> stationDevicesList(Integer stationId) {
        List<DeviceEntity> devicesList = deviceRepository.findByStationIdAndDeviceInUse(stationId, true);
        System.out.println("deviceList: " + devicesList);
        List<DeviceResponse> list = devicesList.stream().map(device ->
                {
                    DeviceResponse deviceConverted = deviceResponseMapper.converToDeviceResponse(device);
                    deviceConverted.setDeviceName(deviceConverted.getDeviceType() + " - " + deviceConverted.getDeviceNo());
                    return deviceConverted;
                }
                ).collect(Collectors.toList());
        return list;
    }
}
