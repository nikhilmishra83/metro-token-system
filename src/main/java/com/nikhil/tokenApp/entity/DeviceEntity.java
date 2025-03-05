package com.nikhil.tokenApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name="device")
public class DeviceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer deviceId;

    private Integer deviceTypeId;
    private String deviceType;
    private Integer deviceNo;
    private Integer stationId;
    private Boolean deviceInUse;
}
