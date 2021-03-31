package com.novicap.checkout.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class BulkPurchaseDiscount implements Discount {

    private final ProductCode productCode;
    private final int minimumNumberOfItems;
    private final BigDecimal discountedPricePerUnit;

    @Override
    public BigDecimal apply(Product product, int purchasedQuantity) {
        if (purchasedQuantity > minimumNumberOfItems) {
            return discountedPricePerUnit.multiply(new BigDecimal(purchasedQuantity));
        } else {
            return product.getListPrice().multiply(new BigDecimal(purchasedQuantity));
        }
    }
}
