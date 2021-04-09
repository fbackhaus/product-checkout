package com.novicap.checkout.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;

@Builder
public class TotalPriceDiscount implements Discount {

    private final BigDecimal minimumSpentValue;
    private final BigDecimal discountPercentage;

    /**
     * Applies a TotalPriceDiscount if the conditions are met
     * A TotalPriceDiscount is a discount applied to the total of the purchase if the conditions are met
     * e.g 10% off if you spend more than €20
     *
     * @param basket containing all the products the customer has purchased
     * @param currentTotal current purchase total amount
     * @return how much to discount from the total amount
     */
    @Override
    public BigDecimal apply(Map<ProductCode, Integer> basket, BigDecimal currentTotal) {
        if (currentTotal.compareTo(minimumSpentValue) > 0) {
            return currentTotal.multiply(BigDecimal.ONE.subtract(discountPercentage));
        }
        return currentTotal;
    }
}
