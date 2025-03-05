package com.nikhil.tokenApp.domain;

import lombok.Data;

@Data
public class DeviceResponse {

    private Integer deviceId;
    private String deviceType;
    private Integer deviceNo;
    private String deviceName;
}
