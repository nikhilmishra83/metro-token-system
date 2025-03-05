package com.nikhil.tokenApp.controller;


import com.nikhil.tokenApp.domain.DeviceStationResponse;
import com.nikhil.tokenApp.domain.MasterStationResponse;
import com.nikhil.tokenApp.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/metro/station")
public class StationController {

    @Autowired
    StationService stationService;

    @GetMapping("/list")
    List<DeviceStationResponse> getStations() {
        return stationService.getDeviceStations();
    }

    @GetMapping("/toList")
    List<MasterStationResponse> getToStations(@RequestParam Integer stationId, @RequestParam Integer routeNo) {
        return stationService.getToStations(stationId, routeNo);
    }
}

