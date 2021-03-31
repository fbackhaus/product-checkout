package com.novicap.checkout.repository;

import com.novicap.checkout.model.Product;
import com.novicap.checkout.model.ProductCode;

import java.util.Map;

public interface ProductRepository {
    Map<ProductCode, Product> getProducts();
}
