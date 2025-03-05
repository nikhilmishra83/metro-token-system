package com.nikhil.tokenApp.domain;

import lombok.Data;

@Data
public class DeviceStationResponse {
    private Integer stationId;
    private String station;
    private Integer stationNo;
    private Integer routeNo;
}
