package com.novicap.checkout.service;

import com.novicap.checkout.model.Discount;
import com.novicap.checkout.model.Product;
import com.novicap.checkout.model.ProductCode;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class DiscountService {

    private final List<Discount> discounts;

    public DiscountService(List<Discount> discounts) {
        this.discounts = discounts;
    }

    /**
     * Calculates full price per product
     * @param product Product purchased
     * @param quantity total quantity purchased of that product
     * @return the full price, given than there is no discount for this product
     */
    public BigDecimal getPriceWithoutDiscount(Product product, int quantity) {
        return product.getPrice().multiply(new BigDecimal(quantity));
    }

    /**
     * Applies discounts to the list price total
     * @param basket containing the products the customer has purchased
     * @param listPriceTotal
     * @return
     */
    public BigDecimal applyDiscounts(Map<ProductCode, Integer> basket, BigDecimal listPriceTotal) {
        for (Discount discount : discounts) {
            BigDecimal amountToDiscount = discount.apply(basket, listPriceTotal);
            listPriceTotal = listPriceTotal.subtract(amountToDiscount);
        }
        return listPriceTotal;
    }
}
