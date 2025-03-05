package com.nikhil.tokenApp.enums;

public enum DeviceTypeEnum {
    TOKEN(1, "token"),
    ENTRY(2, "entry"),
    EXIT(3, "exit"),
    ADMIN(4, "admin");

    private final int deviceTypeId;
    private final String deviceType;

    DeviceTypeEnum(int deviceTypeId, String deviceType) {
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
