package com.novicap.checkout.service;

import com.novicap.checkout.model.Discount;
import com.novicap.checkout.model.Product;
import com.novicap.checkout.model.ProductCode;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public class ShoppingBasketService {

    private Map<ProductCode, Integer> basket;
    private final DiscountService discountService;
    private final ProductService productService;

    public ShoppingBasketService(Map<ProductCode, Discount> priceRules) {
        this.discountService = new DiscountService(priceRules);
        this.productService = new ProductService();
    }

    public void addProductToBasket(ProductCode productCode) {
        Optional<Integer> currentQuantity = Optional.ofNullable(basket.get(productCode));

        if (currentQuantity.isPresent()) {
            basket.put(productCode, currentQuantity.get() + 1);
        } else {
            basket.put(productCode, 1);
        }
    }

    public BigDecimal calculateTotal() {
        return basket.entrySet()
                .parallelStream()
                .map((this::calculateTotalPerProduct))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalPerProduct(Map.Entry<ProductCode, Integer> productCodeIntegerEntry) {
        Product product = productService.getProductInfoFromCode(productCodeIntegerEntry.getKey());
        return discountService.getFinalPricePerProductCode(product, productCodeIntegerEntry.getValue());
    }

}
