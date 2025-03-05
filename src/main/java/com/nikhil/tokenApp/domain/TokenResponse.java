package com.nikhil.tokenApp.domain;

import lombok.Data;

@Data
public class TokenResponse {

    private int tokenId;
    private String tokenNo;
    private Float price;
    private Integer fromStationId;
    private Integer toStationId;
    private String fromStation;
    private String toStation;
    private String tokenDate;
    private String tokenTime;
    private String tokenNote;
}
