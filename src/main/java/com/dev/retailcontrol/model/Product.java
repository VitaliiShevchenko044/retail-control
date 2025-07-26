package com.dev.retailcontrol.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String barcode;

    private String name;
    private String category;
    private String manufacturer;
    private String unit;
    private double price;
    private int quantity;
    private String imagePath;
}