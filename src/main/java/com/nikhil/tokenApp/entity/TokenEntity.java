package com.nikhil.tokenApp.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="token")
public class TokenEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tokenId;

    private String tokenNo;

    private Integer fromStationId;

    private Integer toStationId;

    private Float price;

    private LocalDateTime generatedAt;

    private Integer entryStationId;

    private Integer exitStationId;

    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    private Float travelledKm;

    private Float travellingPrice;

    private Float exitPrice;

    private Integer exitPricePaid;

    private Integer tokenStatus;
}
