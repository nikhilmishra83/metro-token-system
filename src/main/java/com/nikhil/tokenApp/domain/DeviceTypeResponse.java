package com.nikhil.tokenApp.domain;

public class DeviceTypeResponse {
    private int deviceTypeId;
    private String deviceType;

    public DeviceTypeResponse(int deviceTypeId, String deviceType) {
        this.deviceTypeId = deviceTypeId;
        this.deviceType = deviceType;
    }

    public int getDeviceTypeId() {
        return deviceTypeId;
    }

    public String getDeviceType() {
        return deviceType;
    }
}
