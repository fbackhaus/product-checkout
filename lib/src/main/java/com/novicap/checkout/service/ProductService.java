package com.novicap.checkout.service;

import com.novicap.checkout.model.Product;
import com.novicap.checkout.model.ProductCode;
import com.novicap.checkout.repository.ProductRepository;
import com.novicap.checkout.repository.ProductRepositoryFromJsonFile;

import java.util.Map;

public class ProductService {

    private final Map<ProductCode, Product> products;
    private final ProductRepository productRepository;

    public ProductService() {
        productRepository = new ProductRepositoryFromJsonFile();
        products = productRepository.getProducts();
    }

    /**
     * Returns the full product information from a product code
     * @param productCode product scanned
     * @return full product information
     */
    public Product getProductInfoFromCode(ProductCode productCode) {
        return products.get(productCode);
    }
}
