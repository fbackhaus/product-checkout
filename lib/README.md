# Novicap Checkout Library

## Summary

A Java Checkout library that allows you scan products and calculate the final price of a shopping, applying different
kinds of discounts

## Build and Run Tests

```shell
./gradlew clean test
```

## Creating a checkout

### Creating discounts

All discounts implement the [Discount](./src/main/java/com/novicap/checkout/model/Discount.java) interface, that applies
a discount if the conditions are met

#### Special Discounts

A SpecialDiscount contains one or more free items if the customer buys more than x quantity of the item

e.g. 2-for-1 in T-Shirts, 3-for-2 in mugs

```java
SpecialDiscount discount = SpecialDiscount.builder()
        .minimumNumberOfItemsToPurchase(minimumNumberOfItemsToPurchase)
        .numberOfItemsToCharge(numberOfItemsToCharge)
        .build();
```

e.g. a 2-for-1 discount

```java
SpecialDiscount discount = SpecialDiscount.builder()
        .minimumNumberOfItemsToPurchase(2)
        .numberOfItemsToCharge(1)
        .build();
```

#### Bulk Purchase Discounts

A BulkPurchaseDiscount contains a discounted price per item if the customer buys more than x quantity of the item

```java
BulkPurchaseDiscount discount = BulkPurchaseDiscount.builder()
        .minimumNumberOfItems(minToPurchase)
        .discountedPrice(priceToCharge)
        .build();
```

e.g. if the customer buys more than 3 T-Shirts, the price will be €19.00 instead of €20.00

```java
BulkPurchaseDiscount discount = BulkPurchaseDiscount.builder()
        .minimumNumberOfItems(3)
        .discountedPrice(new BigDecimal("19.00"))
        .build();
```

### Creating checkout

At the moment of the creation of the checkout, you can choose if you desire to include discounts, or not.

#### Checkout with discounts

When you instantiate the checkout, you need to pass a map with all the discounts you wish to include.

Example, if you want to add a 2-for-1 special discount in vouchers, and a unit price of €19.00 for T-Shirt if the
customer buys 3 or more

```java
//You need to create a Map<ProductCode, Discount> containing the discounts per product
Map<ProductCode, Discount> discountRules = new HashMap<>();

//Adding voucher discount
SpecialDiscount voucherDiscount = SpecialDiscount.builder()
        .minimumNumberOfItemsToPurchase(2)
        .numberOfItemsToCharge(1)
        .build();

discountRules.put(ProductCode.VOUCHER,voucherDiscount)

BulkPurchaseDiscount tShirtDiscount = BulkPurchaseDiscount.builder()
        .minimumNumberOfItems(3)
        .discountedPrice(new BigDecimal("19.00"))
        .build();

discountRules.put(ProductCode.TSHIRT,tShirtDiscount)

//Create checkout
Checkout checkout = new Checkout(discountRules);
```

#### Checkout with no discounts

Just instantiate the checkout object without a list of discount rules.

```java
Checkout checkout = new Checkout();
```

## Modifying products

If you need to change a product, or it's list price, you need to change
the [products.json](./src/main/resources/products.json) file. It has the following structure

```json
[
  {
    "code": "MUG",
    "name": "Coffee mug",
    "price": 7.50
  }
]
```

This file is read and loaded into memory at the moment of the creation of the checkout. If the client has a problem when
trying to parse the products file, it will throw an `UnreadableProductsFileException`.

## How to use it

If you need to scan a product, just call the `scan()` method in the checkout object.

```java
checkout.scan(productCode);
```

If you have finished scanning products, just call the `total()` method. This will apply the discounts if appliable, and
calculate the total purchase value