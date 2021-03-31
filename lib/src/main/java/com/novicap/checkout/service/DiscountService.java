package com.novicap.checkout.service;

import com.novicap.checkout.model.Discount;
import com.novicap.checkout.model.Product;
import com.novicap.checkout.model.ProductCode;

import java.math.BigDecimal;
import java.util.Map;

public class DiscountService {

    private final Map<ProductCode, Discount> discountRulesPerProduct;

    public DiscountService(Map<ProductCode,Discount> discountRulesPerProduct) {
        this.discountRulesPerProduct = discountRulesPerProduct;
    }

    public BigDecimal getFinalPricePerProductCode(Product product, int quantity) {
        Discount discountRule = discountRulesPerProduct.get(product.getCode());
        return discountRule.apply(product, quantity);
    }
}
