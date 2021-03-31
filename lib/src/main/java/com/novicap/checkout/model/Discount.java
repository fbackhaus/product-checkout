package com.novicap.checkout.model;

import java.math.BigDecimal;

public interface Discount {
    BigDecimal apply(Product product, int purchasedQuantity);
}
