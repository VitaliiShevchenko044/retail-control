package com.dev.retailcontrol.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductCardDto {

    private String name;
    private String manufacturer;
    private String barcode;
    private double price;
    private int quantity;
    private String unit;
    private String imagePath;
}