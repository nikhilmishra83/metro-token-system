package com.nikhil.tokenApp.controller;

import com.nikhil.tokenApp.domain.DeviceResponse;
import com.nikhil.tokenApp.domain.DeviceTypeResponse;
import com.nikhil.tokenApp.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/metro/device")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @GetMapping("/types/list")
    List<DeviceTypeResponse> getDeviceTypes(){
        return deviceService.getDeviceTypes();
    }

    @GetMapping("/list")
    List<DeviceResponse> getStationDevices(@RequestParam Integer stationId){
        System.out.println("/metro/device/list called, stationId passed: " + stationId);
        return deviceService.stationDevicesList(stationId);
    }

}
