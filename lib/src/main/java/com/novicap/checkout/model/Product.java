package com.novicap.checkout.model;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Product {

    private ProductCode code;
    private String name;
    private BigDecimal listPrice;


}
