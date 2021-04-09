package com.novicap.checkout.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;

@Builder
public class BulkPurchaseDiscount implements Discount {

    private final int minimumNumberOfItems;
    private final BigDecimal discountPerUnit;
    private final Product product;

    /**
     * Applies a BulkPurchaseDiscount if the conditions are met
     * A BulkPurchaseDiscount is a discounted price per item if the customer buys more than x quantity of the item
     *
     * @param basket containing all the products the customer has purchased
     * @param currentTotal current purchase total amount
     * @return how much to discount from the total amount
     */
    @Override
    public BigDecimal apply(Map<ProductCode, Integer> basket, BigDecimal currentTotal) {
        int purchasedQuantity = basket.get(product.getCode());

        if (purchasedQuantity >= minimumNumberOfItems) {
            basket.remove(product.getCode());
            return applyPriceDiscount(purchasedQuantity);
        }
        return BigDecimal.ZERO;
    }

    /**
     * Calculates full price with the discount applied
     * @param purchasedQuantity quantity purchased of the product
     * @return final discounted price after multiplying the quantity with the discounted price per item
     */
    private BigDecimal applyPriceDiscount(int purchasedQuantity) {
        return discountPerUnit.multiply(new BigDecimal(purchasedQuantity));
    }
}
