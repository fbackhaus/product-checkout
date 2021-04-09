package com.novicap.checkout.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Product {
    private ProductCode code;
    private String name;
    private BigDecimal price;
}
