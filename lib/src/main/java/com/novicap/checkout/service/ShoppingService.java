package com.novicap.checkout.service;

import com.novicap.checkout.model.Discount;
import com.novicap.checkout.model.Product;
import com.novicap.checkout.model.ProductCode;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ShoppingService {

    private final Map<ProductCode, Integer> basket;
    private final DiscountService discountService;
    private final ProductService productService;

    public ShoppingService(Map<ProductCode, Discount> priceRules) {
        this.basket = new HashMap<>();
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

    private void emptyShoppingBasket() {
        basket.clear();
    }

    public BigDecimal calculateTotal() {
        BigDecimal totalPrice = basket.entrySet()
                .parallelStream()
                .map((this::calculateTotalPerProduct))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        emptyShoppingBasket();
        return totalPrice;
    }

    private BigDecimal calculateTotalPerProduct(Map.Entry<ProductCode, Integer> productCodeIntegerEntry) {
        Product product = productService.getProductInfoFromCode(productCodeIntegerEntry.getKey());
        return discountService.getFinalPricePerProductCode(product, productCodeIntegerEntry.getValue());
    }

}
