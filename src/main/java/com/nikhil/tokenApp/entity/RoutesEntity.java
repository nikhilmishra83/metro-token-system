package com.nikhil.tokenApp.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "routes")
public class RoutesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int routeId;

    private int routeNo;
    private String route;
    private int noOfStations;
    private float routeLength;
    private int routeFirstStation;
    private int routeLastStation;
}
