package com.novicap.checkout.model;

import java.math.BigDecimal;
import java.util.Map;

public interface Discount {
    BigDecimal apply(Map<ProductCode, Integer> basket, BigDecimal currentTotal);
}
