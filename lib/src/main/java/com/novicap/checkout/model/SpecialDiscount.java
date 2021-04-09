package com.novicap.checkout.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;

@Builder
public class SpecialDiscount implements Discount {

    private final int minimumNumberOfItemsToPurchase;
    private final int numberOfItemsToCharge;
    private final Product product;

    /**
     * Applies a SpecialDiscount if the conditions are met
     * A SpecialDiscount contains one or more free items if the customer buys more than x quantity of the product
     * e.g 2-for-1, 3-for-1, 3-for-2, etc
     *
     * @param basket containing all the products the customer has purchased
     * @param currentTotal current purchase total amount
     * @return how much to discount from the total amount
     */
    @Override
    public BigDecimal apply(Map<ProductCode, Integer> basket, BigDecimal currentTotal) {
        int purchasedQuantity = basket.get(product.getCode());

        if (shouldApplyDiscount(purchasedQuantity)) {
            basket.remove(product.getCode());
            return calculateAmountToDiscount(product, purchasedQuantity);
        }
        return BigDecimal.ZERO;
    }

    /**
     * Decides if the conditions are met to apply a special discount or not
     * @param purchasedQuantity purchased quantity of the product
     * @return true if the customer has purchased the minimum quantity to apply the discount, or more
     */
    private boolean shouldApplyDiscount(int purchasedQuantity) {
        return purchasedQuantity >= minimumNumberOfItemsToPurchase;
    }

    private BigDecimal calculateAmountToDiscount(Product product, int purchasedQuantity) {
        int finalQuantity = purchasedQuantity - getQuantityToCharge(purchasedQuantity);
        return product.getPrice().multiply(new BigDecimal(finalQuantity));
    }

    /**
     * Calculates how many items to charge, if the minimum quantity is purchased
     * e.g. If the discount is 2-for-1, we need to charge 1
     * e.g. If the discount is 3-for-2, we need to charge 2
     *
     * @param purchasedQuantity quantity that a customer has purchased
     * @return the quantity that needs to be charged according to the discount
     */
    private int getQuantityToCharge(int purchasedQuantity) {
        if (allItemsFitIntoTheDiscount(purchasedQuantity)) {
            return purchasedQuantity / minimumNumberOfItemsToPurchase * numberOfItemsToCharge;
        }
        return numberOfItemsToCharge + purchasedQuantity - minimumNumberOfItemsToPurchase;
    }

    /**
     * Decides if all items fit into the discount, or there are items that must be charged their regular price
     * e.g. If the discount is 2-for-1, and the customer buys 2, 4, 6... returns true
     * e.g. If the discount is 2-for-1, and the customer buys 1, 3, 5... returns false
     *
     * @param purchasedQuantity quantity that a customer has purchased
     * @return true/false, depending if all items fit into the discount or not
     */
    private boolean allItemsFitIntoTheDiscount(int purchasedQuantity) {
        return purchasedQuantity % minimumNumberOfItemsToPurchase == 0;
    }
}
