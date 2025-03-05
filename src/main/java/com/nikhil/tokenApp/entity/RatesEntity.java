package com.nikhil.tokenApp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rates")
public class RatesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rangeId;

    private String rangeName;
    private float minKm;
    private float maxKm;
    private float price;

}
