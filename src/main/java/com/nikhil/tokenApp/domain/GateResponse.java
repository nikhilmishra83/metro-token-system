package com.nikhil.tokenApp.domain;

import lombok.Data;

@Data
public class GateResponse {

    private Boolean accessAllowed;
    private String message;
}
