package com.novicap.checkout.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class SpecialDiscount implements Discount {

    private final int minimumNumberOfItemsToPurchase;
    private final int numberOfItemsToCharge;

    @Override
    public BigDecimal apply(Product product, int purchasedQuantity) {
        if (purchasedQuantity < minimumNumberOfItemsToPurchase) {
            return product.getPrice().multiply(new BigDecimal(purchasedQuantity));
        }
        int itemsToCharge = getQuantityToCharge(purchasedQuantity);
        return product.getPrice().multiply(new BigDecimal(itemsToCharge));
    }

    /**
     * Method that calculates how many items to charge, if the minimum quantity is purchased
     * e.g. If the discount is 2x1, we need to charge 1
     * e.g. If the discount is 3x2, we need to charge 2
     *
     * @param purchasedQuantity quantity that a customer has purchased
     * @return the quantity that needs to be charged according to the discount
     */
    private int getQuantityToCharge(int purchasedQuantity) {
        return purchasedQuantity / minimumNumberOfItemsToPurchase;
    }
}
