package com.novicap.checkout.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class BulkPurchaseDiscount implements Discount {

    private final int minimumNumberOfItems;
    private final BigDecimal discountedPrice;

    /**
     * Applies a BulkPurchaseDiscount if the conditions are met
     * A BulkPurchaseDiscount is a discounted price per item if the customer buys more than x quantity of the item
     *
     * @param product product purchased
     * @param purchasedQuantity purchased quantity of the product
     * @return price after applying a discount if discount conditions are met, or full price if conditions are not met
     */
    @Override
    public BigDecimal apply(Product product, int purchasedQuantity) {
        return purchasedQuantity >= minimumNumberOfItems ?
                applyPriceDiscount(purchasedQuantity, discountedPrice) :
                applyListPrice(purchasedQuantity, product.getPrice());
    }

    /**
     * Calculates full price without discount
     * @param purchasedQuantity quantity purchased of the product
     * @param price list price of the product
     * @return final list price after multiplying the quantity with the list price per item
     */
    private BigDecimal applyListPrice(int purchasedQuantity, BigDecimal price) {
        return price.multiply(new BigDecimal(purchasedQuantity));
    }

    /**
     * Calculates full price with the discount applied
     * @param purchasedQuantity quantity purchased of the product
     * @param pricePerUnit discounted price per unit
     * @return final discounted price after multiplying the quantity with the discounted price per item
     */
    private BigDecimal applyPriceDiscount(int purchasedQuantity, BigDecimal pricePerUnit) {
        return pricePerUnit.multiply(new BigDecimal(purchasedQuantity));
    }
}
