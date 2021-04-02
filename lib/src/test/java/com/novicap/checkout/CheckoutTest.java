/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.novicap.checkout;

import com.novicap.checkout.model.BulkPurchaseDiscount;
import com.novicap.checkout.model.Discount;
import com.novicap.checkout.model.ProductCode;
import com.novicap.checkout.model.SpecialDiscount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

class CheckoutTest {

    private Checkout checkout;

    @Test
    void createCheckoutWithoutDiscounts() {
        checkout = new Checkout();
        whenIScanTheseProducts(ProductCode.VOUCHER, ProductCode.VOUCHER);
        thenTheExpectedPriceMatchesTheTotal(new BigDecimal(10));

        whenIScanTheseProducts(ProductCode.VOUCHER, ProductCode.TSHIRT, ProductCode.VOUCHER,
                ProductCode.VOUCHER, ProductCode.MUG, ProductCode.TSHIRT, ProductCode.TSHIRT);
        thenTheExpectedPriceMatchesTheTotal(new BigDecimal("82.50"));
    }

    @Test
    void createCheckoutWithSpecialDiscount() {
        Map<ProductCode, Discount> discountRules = new HashMap<>();
        discountRules.put(ProductCode.VOUCHER, getSpecialDiscount(2, 1));

        givenAListOfDiscountRules(discountRules);
        whenIScanTheseProducts(ProductCode.VOUCHER, ProductCode.VOUCHER);
        thenTheExpectedPriceMatchesTheTotal(new BigDecimal(5));
    }

    @Test
    void createCheckoutWithBulkPurchaseDiscount() {
        Map<ProductCode, Discount> discountRules = new HashMap<>();
        discountRules.put(ProductCode.TSHIRT, getBulkPurchaseDiscount(3, new BigDecimal(19).setScale(2)));

        givenAListOfDiscountRules(discountRules);
        whenIScanTheseProducts(ProductCode.TSHIRT, ProductCode.TSHIRT, ProductCode.TSHIRT);
        thenTheExpectedPriceMatchesTheTotal(new BigDecimal(57));
    }

    @Test
    void testCheckoutWithChallengeExamples() {
        Map<ProductCode, Discount> discountRules = new HashMap<>();
        discountRules.put(ProductCode.VOUCHER, getSpecialDiscount(2, 1));
        discountRules.put(ProductCode.TSHIRT, getBulkPurchaseDiscount(3, new BigDecimal(19).setScale(2)));

        givenAListOfDiscountRules(discountRules);
        whenIScanTheseProducts(ProductCode.VOUCHER, ProductCode.TSHIRT, ProductCode.MUG);
        thenTheExpectedPriceMatchesTheTotal(new BigDecimal("32.50"));

        whenIScanTheseProducts(ProductCode.VOUCHER, ProductCode.TSHIRT, ProductCode.VOUCHER);
        thenTheExpectedPriceMatchesTheTotal(new BigDecimal(25));

        whenIScanTheseProducts(ProductCode.TSHIRT, ProductCode.TSHIRT, ProductCode.TSHIRT,
                ProductCode.VOUCHER, ProductCode.TSHIRT);
        thenTheExpectedPriceMatchesTheTotal(new BigDecimal(81));

        whenIScanTheseProducts(ProductCode.VOUCHER, ProductCode.TSHIRT, ProductCode.VOUCHER,
                ProductCode.VOUCHER, ProductCode.MUG, ProductCode.TSHIRT, ProductCode.TSHIRT);
        thenTheExpectedPriceMatchesTheTotal(new BigDecimal("74.50"));
    }

    @Test
    void createCheckoutWith3for2SpecialDiscount() {
        Map<ProductCode, Discount> discountRules = new HashMap<>();
        discountRules.put(ProductCode.MUG, getSpecialDiscount(3, 2));

        givenAListOfDiscountRules(discountRules);
        whenIScanTheseProducts(ProductCode.MUG, ProductCode.MUG);
        thenTheExpectedPriceMatchesTheTotal(new BigDecimal(15));

        givenAListOfDiscountRules(discountRules);
        whenIScanTheseProducts(ProductCode.MUG, ProductCode.MUG, ProductCode.MUG);
        thenTheExpectedPriceMatchesTheTotal(new BigDecimal(15));

        givenAListOfDiscountRules(discountRules);
        whenIScanTheseProducts(ProductCode.MUG, ProductCode.MUG, ProductCode.MUG, ProductCode.MUG);
        thenTheExpectedPriceMatchesTheTotal(new BigDecimal("22.50"));

        givenAListOfDiscountRules(discountRules);
        whenIScanTheseProducts(ProductCode.MUG, ProductCode.MUG, ProductCode.MUG, ProductCode.MUG, ProductCode.MUG);
        thenTheExpectedPriceMatchesTheTotal(new BigDecimal(30));

        givenAListOfDiscountRules(discountRules);
        whenIScanTheseProducts(ProductCode.MUG, ProductCode.MUG, ProductCode.MUG,
                ProductCode.MUG, ProductCode.MUG, ProductCode.MUG);
        thenTheExpectedPriceMatchesTheTotal(new BigDecimal(30));
    }

    private SpecialDiscount getSpecialDiscount(int minToPurchase, int itemsToCharge) {
        return SpecialDiscount.builder()
                .minimumNumberOfItemsToPurchase(minToPurchase)
                .numberOfItemsToCharge(itemsToCharge)
                .build();
    }

    private BulkPurchaseDiscount getBulkPurchaseDiscount(int minToPurchase, BigDecimal priceToCharge) {
        return BulkPurchaseDiscount.builder()
                .minimumNumberOfItems(minToPurchase)
                .discountedPrice(priceToCharge)
                .build();
    }

    private void givenAListOfDiscountRules(Map<ProductCode, Discount> discountRules) {
        checkout = new Checkout(discountRules);
    }

    private void whenIScanTheseProducts(ProductCode... productCodes) {
        for (ProductCode productCode : productCodes) {
            checkout.scan(productCode);
        }
    }


    private void thenTheExpectedPriceMatchesTheTotal(BigDecimal expectedPrice) {
        Assertions.assertEquals(expectedPrice.setScale(2), checkout.total());
    }
}
