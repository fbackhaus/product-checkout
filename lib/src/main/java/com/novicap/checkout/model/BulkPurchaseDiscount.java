package com.novicap.checkout.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class BulkPurchaseDiscount implements Discount {

    private final int minimumNumberOfItems;
    private final BigDecimal discountedPrice;

    @Override
    public BigDecimal apply(Product product, int purchasedQuantity) {
        return purchasedQuantity >= minimumNumberOfItems ?
                applyPriceDiscount(purchasedQuantity, discountedPrice) :
                applyListPrice(purchasedQuantity, product.getPrice());
    }

    private BigDecimal applyListPrice(int purchasedQuantity, BigDecimal price) {
        return price.multiply(new BigDecimal(purchasedQuantity));
    }

    private BigDecimal applyPriceDiscount(int purchasedQuantity, BigDecimal pricePerUnit) {
        return pricePerUnit.multiply(new BigDecimal(purchasedQuantity));
    }
}
