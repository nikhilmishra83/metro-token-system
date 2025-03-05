package com.nikhil.tokenApp.domain;


import lombok.Data;

@Data
public class TokenRequest {

    private int fromStationId;
    private int toStationId;
    private float price;
}
