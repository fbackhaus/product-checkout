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

    public BigDecimal getFinalPricePerProductCode(Product product, int quantity) {
        Optional<Discount> discountRule = Optional.ofNullable(discountRulesPerProduct.get(product.getCode()));
        if (discountRule.isPresent()) {
            return discountRule.get().apply(product, quantity);
        }
        return getPriceWithoutDiscount(product, quantity);
    }

    private BigDecimal getPriceWithoutDiscount(Product product, int quantity) {
        return product.getPrice().multiply(new BigDecimal(quantity));
    }
}
