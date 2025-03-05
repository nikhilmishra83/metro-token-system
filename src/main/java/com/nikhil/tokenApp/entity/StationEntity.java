package com.nikhil.tokenApp.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;

@Data
@Entity
@Table(name = "station")
public class StationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stationId;

    private String station;
    private Time openingTime;
    private Time closingTime;
    private Integer routeNo;
    private Integer stationNo;
    private Float rootDistance;
}
