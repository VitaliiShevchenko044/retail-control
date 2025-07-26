package com.dev.retailcontrol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateForm {

    @NotBlank(message = "Поле не може бути пустим")
    private String barcode;

    @NotBlank(message = "Поле не може бути пустим")
    private String name;

    @NotBlank(message = "Поле не може бути пустим")
    private String manufacturer;

    @NotNull(message = "Поле не може бути пустим")
    @PositiveOrZero(message = "Ціна не може бути від`ємною")
    private Double price;

    @NotNull(message = "Поле не може бути пустим")
    @PositiveOrZero(message = "Кількість не може бути від`ємною")
    private Integer quantity;

    @NotBlank
    private String category;

    @NotBlank
    private String unit;
}