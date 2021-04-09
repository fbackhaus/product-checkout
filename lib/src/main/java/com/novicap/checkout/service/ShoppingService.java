package com.novicap.checkout.service;

import com.novicap.checkout.model.Discount;
import com.novicap.checkout.model.Product;
import com.novicap.checkout.model.ProductCode;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ShoppingService {

    private final Map<ProductCode, Integer> basket;
    private final DiscountService discountService;
    private final ProductService productService;

    public ShoppingService(List<Discount> discounts) {
        this.basket = new HashMap<>();
        this.discountService = new DiscountService(discounts);
        this.productService = new ProductService();
    }

    /**
     * Adds a productCode to the basket and calculates the total number of items per product
     * @param productCode product code scanned
     */
    public void addProductToBasket(ProductCode productCode) {
        Optional<Integer> currentQuantity = Optional.ofNullable(basket.get(productCode));

        if (currentQuantity.isPresent()) {
            basket.put(productCode, currentQuantity.get() + 1);
        } else {
            basket.put(productCode, 1);
        }
    }

    /**
     * Clears the basket of all items
     */
    private void emptyShoppingBasket() {
        basket.clear();
    }

    /**
     * Calculates the total price of the items in the basket
     * @return total price of the items in the basket after applying discounts
     */
    public BigDecimal calculateTotal() {
        BigDecimal listPriceTotal = basket.entrySet()
                .parallelStream()
                .map((this::calculateListPrice))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal finalPrice = discountService.applyDiscounts(basket, listPriceTotal);

        emptyShoppingBasket();
        return finalPrice;
    }

    /**
     *
     * @param productCodeIntegerEntry each one of the elements in the basket (ProductCode, quantityPurchasedOfTheItem)
     * @return total list price without applying any discount
     */
    private BigDecimal calculateListPrice(Map.Entry<ProductCode, Integer> productCodeIntegerEntry) {
        Product product = productService.getProductInfoFromCode(productCodeIntegerEntry.getKey());
        return discountService.getPriceWithoutDiscount(product, productCodeIntegerEntry.getValue());
    }

}
