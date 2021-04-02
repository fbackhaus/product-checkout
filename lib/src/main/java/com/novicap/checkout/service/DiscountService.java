package com.novicap.checkout.service;

import com.novicap.checkout.model.Discount;
import com.novicap.checkout.model.Product;
import com.novicap.checkout.model.ProductCode;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public class DiscountService {

    private final Map<ProductCode, Discount> discountRulesPerProduct;

    public DiscountService(Map<ProductCode, Discount> discountRulesPerProduct) {
        this.discountRulesPerProduct = discountRulesPerProduct;
    }

    /**
     * Checks if there's a discount to be applied to a product, and applies it
     * @param product Product purchased
     * @param quantity total quantity purchased of that product
     * @return the discounted price if there's a discount, or the full price if there is not
     */
    public BigDecimal getFinalPricePerProductCode(Product product, int quantity) {
        Optional<Discount> discountRule = Optional.ofNullable(discountRulesPerProduct.get(product.getCode()));
        if (discountRule.isPresent()) {
            return discountRule.get().apply(product, quantity);
        }
        return getPriceWithoutDiscount(product, quantity);
    }

    /**
     * Calculates full price per product
     * @param product Product purchased
     * @param quantity total quantity purchased of that product
     * @return the full price, given than there is no discount for this product
     */
    private BigDecimal getPriceWithoutDiscount(Product product, int quantity) {
        return product.getPrice().multiply(new BigDecimal(quantity));
    }
}
